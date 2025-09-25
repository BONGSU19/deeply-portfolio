<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
	<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
</sec:authorize>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navigation.css">

      

  <!-- Content -->
  <div style="height: 2000px; padding: 20px;">
    <h1>My Website</h1>
    <p>Scroll down to see the floating navigation in the bottom-right corner.</p>
  </div>

  <!-- Floating Navigation -->
  <div class="floating-nav">
    <ul>
      <li><a href="#home" title="Home">🏠</a></li>
      <li><a href="#about" title="About">ℹ️</a></li>
      <li><a href="#services" title="Services">🛠️</a></li>
      <li><a href="#contact" title="Contact">📧</a></li>
    </ul>
  </div>
<script src="${pageContext.request.contextPath}/assets/js/navigation.js"></script>