<%-- 
    Document   : list
    Created on : 17.12.2015, 22:40:34
    Author     : Jaroslav Cechak
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tpl:template title="New pokemon">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/menu/pokemon/create"
                   modelAttribute="pokemonWrapper" cssClass="form-horizontal">
            <div class="form-group">
                <form:label path="trainerId" cssClass="col-sm-2 control-label">Trainer</form:label>
                    <div class="col-sm-10">
                    <form:select path="trainerId" cssClass="form-control">
                        <c:forEach items="${trainers}" var="trainer">
                            <form:option value="${trainer.id}">${trainer.name} ${trainer.surname}</form:option>
                        </c:forEach>
                    </form:select>
                    <p class="help-block"><form:errors path="trainerId" cssClass="error"/></p>
                </div>
            </div>
            <div class="form-group ${name_error?'has-error':''}">
                <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
                    <div class="col-sm-10">
                    <form:input path="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${nickname_error?'has-error':''}">
                <form:label path="nickname" cssClass="col-sm-2 control-label">Nickname</form:label>
                    <div class="col-sm-10">
                    <form:textarea cols="80" rows="20" path="nickname" cssClass="form-control"/>
                    <form:errors path="nickname" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
                    <div class="col-sm-10">
                    <form:select path="type" cssClass="form-control">
                        <c:forEach items="${types}" var="type">
                            <form:option value="${type}">${type}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="type" cssClass="error"/>
                </div>
            </div>
            <div class="form-group ${skillLevel_error?'has-error':''}" >
                <form:label path="skillLevel" cssClass="col-sm-2 control-label">Skill</form:label>
                    <div class="col-sm-10">
                    <form:input path="skillLevel" cssClass="form-control"/>
                    <form:errors path="skillLevel" cssClass="help-block"/>
                </div>
            </div>   

            <button class="btn btn-primary" type="submit">Create pokemon</button>
        </form:form>

    </jsp:attribute>
</tpl:template>