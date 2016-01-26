<%-- 
    Document   : list
    Created on : 17.12.2015, 22:40:34
    Author     : Jaroslav Cechak
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tpl:template title="Pokemons">
    <jsp:attribute name="body">

        <div class="row">
            <!-- Only admin can manipulate with data -->
            <sec:authorize access="hasRole('ADMIN')">
                <div class="col-lg-2">
                    <a href="${pageContext.request.contextPath}/menu/pokemon/newform" class="btn btn-primary">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        New pokemon
                    </a>
                </div>
            </sec:authorize>

            <div class="col-lg-5">
                <div class="input-group">
                    <form:form method="post" action="${pageContext.request.contextPath}/menu/pokemon/withtype" modelAttribute="pokemonWrapper">
                        <span class="input-group-btn">
                            <form:select path="type" cssClass="form-control">
                                <c:forEach items="${types}" var="type">
                                    <form:option value="${type}">${type}</form:option>
                                </c:forEach>
                            </form:select>
                            <button class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-search"/>
                                Search
                            </button>
                        </span>
                    </form:form>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="input-group">
                    <form:form method="post" action="${pageContext.request.contextPath}/menu/pokemon/withname" modelAttribute="pokemonWrapper">

                        <span class="input-group-btn">
                            <form:input path="name" cssClass="form-control"/>
                            <button class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-search"/>
                                Search
                            </button>
                        </span>
                    </form:form>
                </div>
            </div>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>nickname</th>
                    <th>type</th>
                    <th>skill</th>
                        <sec:authorize access="hasAnyRole('ADMIN','STAFF')">
                        <th>trainer</th>
                        <th></th>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                        <th></th>
                        </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pokemons}" var="pokemon">
                    <tr>
                        <td><c:out value="${pokemon.id}"/></td>
                        <td><c:out value="${pokemon.name}"/></td>
                        <td><c:out value="${pokemon.nickname}"/></td>
                        <td><c:out value="${pokemon.type}"/></td>
                        <td><c:out value="${pokemon.skillLevel}"/></td>
                        <sec:authorize access="hasAnyRole('ADMIN','STAFF')">
                            <td>
                                <a href="${pageContext.request.contextPath}/menu/trainer/view/${pokemon.trainerId}" class="btn btn-primary">
                                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                    Trainer details
                                </a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/menu/pokemon/view/${pokemon.id}" class="btn btn-primary">
                                    View pokemon details
                                </a>

                            </td>
                        </sec:authorize>
                        <!-- Only admin can manipulate with data -->
                        <sec:authorize access="hasRole('ADMIN')">
                            <td>

                                <form:form method="post" action="${pageContext.request.contextPath}/menu/pokemon/delete/${pokemon.id}">
                                    <button class="btn btn-primary btn-danger">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                        Remove pokemon
                                    </button>
                                </form:form>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</tpl:template>