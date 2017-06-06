<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Scientist"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>BioBay</title>  
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link href="css/default.css" rel="stylesheet" type="text/css" media="screen">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <link href='https://fonts.googleapis.com/css?family=Oxygen:400,300,700' rel='stylesheet' type='text/css'>
</head>
<body>
<header>
    <nav id="header-nav" class="navbar navbar-default" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div class="navbar-brand">
            <a href="basicSearch.jsp"><h1>BioBay</h1></a>
          </div>
        </div>
        <%
        Scientist scientist = (Scientist) session.getAttribute("scientist"); 
        %>
        <div id="collapsable-nav" class="collapse navbar-collapse">
           <ul id="nav-list" class="nav navbar-nav navbar-right">
             <li class="login"><a href="basicSearch.jsp">Hello <%=scientist.getUsername()%>  !</a></li>
             <li class="account"><a href="memberProfile.jsp"> My Account</a></li>
             <li class="contact"><a href="#"> Contact Us</a></li>
           </ul>
        </div>
        
        <div id="collapsable-nav" class="collapse navbar-collapse">
          <ul id="info" class="nav navbar-nav navbar-right">
            <li><a href="basicSearch.jsp">Home</a></li>
            <li><a href="href/area.html">Labs</a></li>
            <li><a href="href/type.html">Reagents</a></li>
            <li><a href="shareReagent.jsp">Share Reagent</a></li>
          </ul>
        </div>
      </div>
    </nav>
</header>
<%
String lid = request.getParameter("lid");
String rname = request.getParameter("rname");
%>
<div id="result" class="container">
  <form id="request_form" name="request" method="post" action="requestResult.jsp">  
  <table width="100%" id="requestInfo">
    <tr>
      <td align="left" valign="top">
   <h3>Request Reagent</h3>
   <p></p>
   <p></p>
   <table width="100%">
                  <tr><td width="35%" align="right" valign="middle"><span>*How much/many do you want</span></td>
                      <td width="65%" align="left" valign="middle"><input name="rqqty" type="text" id="rqqty"></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>*What is the proportion of the total</span></td>
                      <td width="65%" align="left" valign="middle"><input name="prop" type="text" id="prop" placeholder="range: 0-1, e.g. 0.5"></td></tr>
                  <tr><td width="35%" align="left" valign="middle"><input name="lid" type="hidden" id="lid" value="<%=lid%>"></td>
                      <td width="65%" align="left" valign="middle"><input name="rname" type="hidden" id="rname" value="<%=rname%>"></td></tr>
                  
    </table>
            </td>
          </tr>
        </table>
        <p></p>
        <input type="submit" value="Submit" style="position:relative; left: 460px;">
  </form>
</div>

<footer class="panel-footer">
    <div class="container">
      <div class="row">
        <section id="hours" class="col-sm-4">
          <span>Contact Hours:</span><br>
          Monday-Friday: 8am - 6pm<br>
          Saturday-Sunday: 9am - 5pm<br>
          <hr class="visible-xs">
        </section>
        <section id="address" class="col-sm-4">
          <span>Address:</span><br>
          55 North Lake Ave<br>
          Worcester, MA 01605
          <p>* </p>
          <hr class="visible-xs">
        </section>
        <section id="other" class="col-sm-4">
          <p>"Join in our network today and get extra bbpoints for reagent!"</p>
          <p>""</p>
        </section>
      </div>
      <div class="text-center">&copy; 2016 All Rights Reserved. BioBay is a registered trademark of BioBay, Inc.</div>
    </div>
  </footer>
  
  <script src="js/jquery-2.1.4.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/script.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script> 
</body>
</html>