<%-- 
    Document   : Student
    Created on : May 15, 2024, 2:56:06 PM
    Author     : LENOVO
--%>


<%@page contentType="text/html" pageEncoding="UTF-8" import="model.*,java.util.*" errorPage="error.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="w-input container bg-light text-black text-center py-3">

    <div class = "center">


        <form name= "generateST" action="display" method = "get">
            <table>
                <tr>
                    <td>Enter name of student: </td>
                    <td><input type="text" name="nameToSearch" value="${name}"/></td>
                    <td><input type="submit" value="generate" /></td>
                </tr>
            </table>
        </form> 
                    
        

        <c:if test="${not empty students}">           


            <table class ="list-table" border='1'>
                <thead><tr><th>Id</th><th>Name</th><th>Gender</th><th>Date of Birth</th></tr></thead></br>
                            <c:forEach var ="student" items='${students}'>
                    <form name= 'update' action='display' method = 'post'>
                        <input type='hidden' name='method' value="update">
                        <tr>
                            <td name ='id'>${student.getId()}</td>
                            <td><input type="text" name="name_${student.getId()}" value="${student.getName()}" ${disabled}></td>
                            <td><input name="isMale_${student.getId()}" type="checkbox" ${disabled}  ${student.isGender() ? 'checked' : ''}></td>
                            <td><input type="text" name="dob_${student.getId()}" value="${student.getDob()}" ${disabled}></td>
                        <input type='hidden' name='id' value=${student.getId()}> 
                        </tr>
                    </c:forEach>
                    <input type="hidden" name="nameToSearch" value="${name}"/>
            </table>

            <c:if test="${not empty sessionScope.username && sessionScope.role == 'admin'}">
                </br>
                <tr><button type='submit'>Update</button>
                </form>
                <button onclick="showDeleteOverlay()">Delete Student</button>
                <button onclick="showInsertOverlay()">Add Student</button></tr>
            </c:if>



            <br/></br>
            <!--<p>${status}</p>-->
        </div>
        <!--<button onclick="showOverlay()">Delete Student</button>-->
        <div id="delete-overlay" class="overlay center">
            <div class="overlay-content">
                <span class="close-btn" onclick="hideDeleteOverlay()">&times;</span>
                <table  border='1'>
                    <tr><th>Id</th><th>Name</th><th>Gender</th><th>Date of Birth</th><th>Select</th></tr></br>
                            <c:forEach var="student" items="${students}">
                        <form name= 'delete' action='display' method = 'post'>
                            <input type='hidden' name='method' value="delete">
                            <tr>
                                <!--<td name ='size'>${students.size()}</td>-->
                                <td name ='id'>${student.getId()}</td>
                                <td name ='name'>${student.getName()}</td>
                                <td><input name="isMale" type="checkbox"  ${student.isGender() ? 'checked' : '' }></td>
                                <td name ='dob'>${student.getDob()}</td>
                                <td><input name="isDelete" type="checkbox" value =${student.getId()} ></td
                                </td>                            </tr>
                            </c:forEach>
                        <input type="hidden" name="nameToSearch" value="${name}"/>

                </table>
                <c:if test="${not empty sessionScope.username && sessionScope.role == 'admin'}">
                    </br><tr><button type='submit'>Delete</button></tr>
                </c:if>

                </form>



                <br/><br/>
            </div>
        </div>

        <!-- div insert ------------------------------------------------------------------------------------------------------->

        <div id="insert-overlay" class="overlay center">
            <div class="overlay-content">
                <span class="close-btn" onclick="hideInsertOverlay()">&times;</span>
                <table border='1'>
                    <tr><th>Name</th><th>Gender</th><th>Date of Birth</th></tr></br>

                    <form name= 'insert' action='display' method = 'post'>
                        <input type="hidden" name="nameToSearch" value="${name}"/>
                        <input type='hidden' name='method' value="insert">
                        <tr>
                            <td><input type="text" name="name"></td>
                            <td><input name="isMale" type="checkbox"></td>
                            <td><input type="text" name="dob"></td>
                        </tr>


                </table>
                </br><tr><button type='submit'>Add Student</button></tr>

                </form>



                <br/><br/>
            </div>
        </div>

    </c:if>

    <p>${status}</p>
    <br/>
    <a id ="logout" href="logout">Logout</a> <br/>
</div>




<br/>






<!-- Footer Section -->

<!-- Bootstrap JS, Popper.js, and jQuery -->



