<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Scientist" import="java.util.ArrayList, model.Request"%>
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
             <li class="login"><a href="index.html">Hello <%=scientist.getUsername()%>  !</a></li>
             <li class="account"><a href="memberProfile.jsp"> My Account</a></li>
             <li class="contact"><a href="#"> Contact Us</a></li>
           </ul>
        </div>
        
        <div id="collapsable-nav" class="collapse navbar-collapse">
          <ul id="info" class="nav navbar-nav navbar-right">
            <li><a href="basicSearch.jsp">Home</a></li>
            <li><a href="lab.html">Labs</a></li>
            <li><a href="reagent.html">Reagents</a></li>
            <li><a href="shareReagent.jsp">Share Reagent</a></li>
          </ul>
        </div>
      </div>
    </nav>
</header>

<% String[] sp=new String[6];
  sp = scientist.viewProfile();
  ArrayList<Request> rl = new ArrayList<Request>();
  rl = scientist.getRequest();
%>
<div id="result" class="container">
  <p><h3>Your Profile</h3></p><hr>
    <table width="100%" id="memberInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Username :</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=sp[2] %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Full Name :</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=sp[3] %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Institute :</span></td>
                      <td width="65%" align="left" valign="middle"><%=sp[0] %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Lab Name :</span></td>
                      <td width="65%" align="left" valign="middle"><%=sp[1] %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Title :</span></td>
                      <td width="65%" align="left" valign="middle"><%=sp[4] %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>When did you joined the lab :</span></td>
                      <td width="65%" align="left" valign="middle"><%=sp[5] %></td></tr>
      </table><hr>
      <form id="lab" name="lab" action="labprofile.jsp" method="post">
        <input type="submit" value="View Lab Account" style="position:relative; left:900px; top:-310px;">
      </form> 
    <p><h3>Request History</h3></p><hr>
    <% for (int i = 0; i < rl.size(); i++) { %>
    <div class="row">
      <div class="col-xs-12 col-sm-6">
        <table width="100%" id="requestInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Request Date :</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=rl.get(i).getRqdate() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Name :</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=rl.get(i).getRname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Lab ID :</span></td>
                      <td width="65%" align="left" valign="middle"><%=rl.get(i).getLid() %></td></tr>
        </table>
      </div>
      <div class="col-xs-12 col-sm-6">
        <table width="100%">
                  <tr><td width="35%" align="right" valign="middle"><span>Requested Quantity/Amount :</span></td>
                      <td width="65%" align="left" valign="middle"><%=rl.get(i).getRqqty() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Paid bbpoints :</span></td>
                      <td width="65%" align="left" valign="middle"><%=rl.get(i).getPayp() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle">
                  <form id="review" name="review" action="reviewReagent.jsp" method="post">
                    <input type="hidden" name="lid" id="lid" value="<%=rl.get(i).getLid()%>">
                    <input type="hidden" name="rname" id="rname" value="<%=rl.get(i).getRname()%>">
                    <input type="submit" value="Review Reagent" style="position:relative; left:270px; top:10px;"></form></td></tr>
        </table>
      </div>
     </div><hr>
     <%} %>
</div>

<footer class="panel-footer">
    <div class="container">
      <div class="row">
        <section id="hours" class="col-sm-4">
          <span>Contact Us</span><br>
          <p></p>
          800-348-2983<br>
          Monday-Friday: 8am - 6pm<br>
          Saturday-Sunday: 9am - 5pm<br>
          <hr class="visible-xs">
        </section>
        <section id="address" class="col-sm-4">
          <span>Address:</span><br>
          <p></p>
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