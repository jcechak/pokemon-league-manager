<%-- 
    Document   : view
    Created on : 18.12.2015, 11:14:17
    Author     : Jaroslav Cechak
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tpl:template title="Pokemon">
    <jsp:attribute name="body">

        <div class="container">
            <table class="table">
                <tbody>
                    <tr>
                        <td>Id</td>
                        <td><c:out value="${pokemon.id}"/></td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td><c:out value="${pokemon.name}"/></td>
                    </tr>
                    <tr>
                        <td>Nickname</td>
                        <td><c:out value="${pokemon.nickname}"/></td>
                    </tr>
                    <tr>
                        <td>Type</td>
                        <td><c:out value="${pokemon.type}"/></td>
                    </tr>
                    <tr>
                        <td>Skill</td>
                        <td><c:out value="${pokemon.skillLevel}"/></td>
                    </tr>
                </tbody>
            </table>     

            <div class="panel panel-default">
                <div class="panel-heading">Trainer</div>
                <div class="panel-body text-center">
                    <h3><c:out value="${trainer.name}"/> <c:out value="${trainer.surname}"/></h3>
                    <br/>
                    <a href="${pageContext.request.contextPath}/menu/trainer/view/${pokemon.trainerId}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        More trainer details
                    </a> 
                    <a href="${pageContext.request.contextPath}/menu/pokemon/withtrainer/${pokemon.trainerId}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        List all pokemons of this trainer
                    </a>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">Operations with pokemon</div>
                <div class="panel-body">
                    <div class="row text-center">
                        <div class="col-sm-3">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changeSkill">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"/>
                                Change skill
                            </button>
                        </div>
                        <div class="col-sm-3">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changeTrainer">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"/>
                                Change trainer
                            </button>
                        </div>

                        <div class="col-sm-3">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#trade">
                                <span class="glyphicon glyphicon-refresh" aria-hidden="true"/>
                                Trade pokemon
                            </button>
                        </div>

                        <div class="col-sm-3">
                            <form method="get" action="${pageContext.request.contextPath}/menu/pokemon/delete/${pokemon.id}">
                                <a href="${pageContext.request.contextPath}/menu/pokemon/delete/${pokemon.id}" class="btn btn-primary btn-danger">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    Remove pokemon
                                </a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Modal skill -->
        <div id="changeSkill" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <form:form method="post" action="${pageContext.request.contextPath}/menu/pokemon/changeskill/${pokemon.id}" modelAttribute="pokemonWrapper">

                    <span class="input-group-btn">
                    </span>

                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Change skill</h4>
                        </div>
                        <div class="modal-body">
                            <p>Write new value of pokemon's skill.</p>
                            <form:input path="skillLevel" cssClass="form-control"/>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Change</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </form:form>

            </div>
        </div>

        <!-- Modal trainer -->
        <div id="changeTrainer" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <form:form method="post" action="${pageContext.request.contextPath}/menu/pokemon/changetrainer/${pokemon.id}" modelAttribute="pokemonWrapper">

                    <span class="input-group-btn">
                    </span>

                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Change trainer</h4>
                        </div>
                        <div class="modal-body">
                            <p>Select the new trainer.</p>
                            <form:select path="trainerId" cssClass="form-control">
                                <c:forEach items="${trainers}" var="trainer">
                                    <form:option value="${trainer.id}">${trainer.name} ${trainer.surname}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Change</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </form:form>

            </div>
        </div>

        <!-- Modal trade -->
        <div id="trade" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->

                <span class="input-group-btn">
                </span>

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Change trainer</h4>
                    </div>
                    <div class="modal-body">
                        <p>Select the pokemon for trade.</p>
                        <select id="selector" class="selectpicker">
                            <c:forEach items="${pokemons}" var="pokemonIter">
                                <option value="<c:out value="${pokemonIter.id}"/>" <c:if test="${pokemon.id == pokemonIter.id}">selected="selected"</c:if>>
                                    <c:out value="${pokemonIter.nickname}"/> (<c:out value="${pokemonIter.name}"/>)
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <a id="link" href="${pageContext.request.contextPath}/menu/pokemon/trade/${pokemon.id}/${pokemon.id}" class="btn btn-primary">Trade</a>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>

        <script>
            $(document).ready(function () {
                $("select#selector").change(function () {
                    var selectedid = $("#selector option:selected").val();
                    $('#link').attr('href', '<c:out value="${pageContext.request.contextPath}"/>/menu/pokemon/trade/<c:out value="${pokemon.id}"/>/' + selectedid);
                });
            });
        </script>

    </jsp:attribute>
</tpl:template>