<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Scientist" import="model.LabReagent" import="model.Lab" import="java.util.ArrayList"%>
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

<div id="result" class="container">
<h3>Search Results</h3>
<%
  ArrayList<LabReagent> lrl = (ArrayList<LabReagent>) request.getAttribute("labReagentList"); 
  ArrayList<Lab> labList = (ArrayList<Lab>) request.getAttribute("labList");
  ArrayList<Integer> count = (ArrayList<Integer>) request.getAttribute("count");
  ArrayList<Double> labavr = (ArrayList<Double>) request.getAttribute("labavr");
  if (lrl != null) {%>
  <h4>The lab(s) have the best reagent you want are as below :</h4><hr>
  <% for (int i = 0; i < lrl.size(); i++) { %>
  <div class="row">
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="reagentInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Name:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lrl.get(i).getRname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Source:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lrl.get(i).getRsource() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Type:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getRtype() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Expiration Date:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getExpDate() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Average Score:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getAvrscore() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Quantity/Amount:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getSqty() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Asking bbpoints:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getAskp() %></td></tr>
       </table>
    </div>
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="labInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Lab ID:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lrl.get(i).getLid() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Lab Name:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=lrl.get(i).getLname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Institute:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getInstitute() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Address:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getAddress() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Research Area:</span></td>
                      <td width="65%" align="left" valign="middle"><%=lrl.get(i).getRsarea() %></td></tr>
                  <tr><td width="35%" align="right" valign="middle">
                  <form id="request" name="request" action="requestReagent.jsp" method="post">
                    <input type="hidden" name="lid" id="lid" value="<%=lrl.get(i).getLid()%>">
                    <input type="hidden" name="rname" id="rname" value="<%=lrl.get(i).getRname()%>">
                    <input type="submit" value="Request Reagent" style="position:relative; left:300px; top:10px;"></form></td></tr>  
       </table>   
    </div>
  </div><hr>
  <%}
  } else if (labavr != null && !labavr.isEmpty()) { %>
   <h4>The average score of the reagents the labs have shared are as below (highest to lowest) :</h4><hr>
   <% for (int i = 0; i < labList.size(); i++) { %>
  <div class="row">
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="reagentInfo">
                  <tr><td width="35%" align="right" valign="middle"><span>Lab ID:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getLid() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Lab Name:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getLname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Institute:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getInstitute() %></span></td></tr>
      </table>
    </div>
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="labInfo">    
                  <tr><td width="35%" align="right" valign="middle"><span>Address:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getAddress() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Research Area:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getRsarea() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Average Score:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labavr.get(i) %></span></td></tr> 
       </table>        
	  </div>
  </div><hr>
  <%}
  } else if (count != null && !count.isEmpty()) { %>
  <h4>The number of the reagents the labs have shared are as below (highest to lowest) :</h4><hr>
   <% for (int i = 0; i < labList.size(); i++) { %>
  <div class="row">
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="reagentInfo">
      <tr><td width="35%" align="right" valign="middle"><span>Lab ID:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getLid() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Lab Name:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getLname() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Institute:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getInstitute() %></span></td></tr>
      </table>
    </div>
    <div class="col-xs-12 col-sm-6">
      <table width="100%" id="labInfo">    
                  <tr><td width="35%" align="right" valign="middle"><span>Address:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getAddress() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Research Area:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=labList.get(i).getRsarea() %></span></td></tr>
                  <tr><td width="35%" align="right" valign="middle"><span>Reagent Counts:</span></td>
                      <td width="65%" align="left" valign="middle"><span><%=count.get(i) %></span></td></tr> 
       </table>        
	  </div>
  </div><hr>
<%} } %>
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