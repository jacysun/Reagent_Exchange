<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Scientist, model.LabReagent, java.util.Date, java.text.ParseException, java.sql.CallableStatement, java.sql.Connection, java.sql.DriverManager, java.sql.Types"%>
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
             <li class="login"><a href="memberProfile.jsp">Hello <%=scientist.getUsername()%>  !</a></li>
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
String rqqty = request.getParameter("rqqty");
String prop = request.getParameter("prop");
double qty = 0;
try {
	qty = Double.parseDouble(request.getParameter("prop"));
} catch (NumberFormatException nfe) {
	nfe.printStackTrace();
}

double payp = 0;
payp = scientist.requestReagent(lid, rname, rqqty, qty);

LabReagent lr = new LabReagent();
lr = lr.getReagentInfo(lid, rname);
%>
<div id="result" class="container">	
<h3>Please confirm the information of the reagent you are requesting.</h3><hr>
<div class="row">
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="reagentInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Name:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getRname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Source:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getRsource() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Type:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lr.getRtype() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Expiration Date:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lr.getExpDate() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Average Score:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lr.getAvrscore() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Quantity/Amount:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lr.getSqty() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Asking bbpoints:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lr.getAskp() %></td></tr>
       </table>
    </div>
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="labInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Lab ID:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getLid() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Lab Name:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getLname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Institute:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getInstitute() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Address:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getAddress() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Research Area:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lr.getRsarea() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"> 
       </table>   
    </div>
  </div><hr>
<p style="position:relative; left:780px;font-size:18px;">You requested <%=rqqty %></p>
<p style="position:relative; left:780px;font-size:18px;">This request will cost you <%=payp %> bbpoints</p>

<button type="button" onclick="confirm()" style="position:relative; left:850px; top:10px;font-size:20px;">Confirm Request</button>
<p></p>
</div>

<script>
function confirm() {
<%if (payp > 0) {%>
	window.alert("Your request has been submitted successfully!");
	window.location="basicSearch.jsp";
<%} else {%>
	window.alert("Sorry we cannot process your request...");
<%}%>
}
</script>


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