<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
<head>
    <title>BDSM Next Generation v0.1</title>
<s:head theme="xhtml" />
<sj:head jqueryui="true" loadAtOnce="true" jquerytheme="sunny" />
<style>
body, html {
    width:100%;
    height:100%;
    margin:0;
    padding:0;
    overflow:hidden;
    font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
	font-size: 11px;
}
form {
    padding:0px;
    padding-top:5px;
    margin:0px;
}
hr {
height:1px;
width:100%;
border:0px solid black;
border-top:1px solid black;
}
.wwFormTable {
    margin:0;
    padding:0;
    overflow:hidden;
    font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
	font-size: 11px;
    border:1px solid black;
    background-color:white;
}
.tdLabel {
    text-align:right;
    vertical-align:baseline;
}
td {
    vertical-align:baseline;
}
</style>
<script type="text/javascript">
    function _resize() {
		$("#frameForm").position({
			at: "right top",
			my: "right top",
			of: $("body"),
            offset: "-20px 20px",
            collision: "none"
		});
		$(".errorMessage").position({
			at: "right bottom",
			my: "right top",
			of: $("#frameForm"),
            offset: "0px 50px",
            collision: "none"
		});
    }
    $(function() {

        $("#frameForm").width($("table:first").width());
        $(window).resize(_resize);
        _resize();
    })
</script>
</head>
<body style="background-color:white">
<div style="position:relative; float:right">
<s:actionerror id="idError" cssStyle="position:absolute; right:0px; top:0px; width:500px" />
</div>
<span style="position:relative; float:left">
    <img src='./Logo-Bank-Danamon.png' width:663px
         style='position:absolute; left:0px; top:0px; width:330px; height:73px; z-index:-1; filter:alpha(opacity=100); -moz-opacity:1.0; -khtml-opacity: 1.0; opacity: 1.0;' />
</span>
<span style="position:relative; float:right">
    <img src='./banner-main.jpg' 
         style='position:absolute; right:0px; top:160px; width:995px; height:380px; z-index:-1; filter:alpha(opacity=99); -moz-opacity:0.99; -khtml-opacity: 0.99; opacity: 0.99;' />
</span>
<h1 style="text-align:center; font-family:Verdana; color:#ff6600">
    <span style="font-weight:bold; color:#006600">B</span>ank <span style="font-weight:bold; color:#006600">D</span>anamon <span style="font-weight:bold; color:#006600">S</span>upplementary <span style="font-weight:bold; color:#006600">M</span>odules
</h1>
<div id="frameForm">
<span class="ui-widget ui-widget-header ui-corner-top" style="border:1px solid black; border-bottom:0px solid black; padding:5px; margin:0">Login Form</span>
<s:form id="frmLogin" action="login" method="post" focusElement="frmLogin_idUser" validate="true" namespace="/">
    <s:textfield
        name="idUser"
        key="label.idUser"
        required="true"
        size="25"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        cssStyle="text-transform:uppercase"
        onblur="javascript:this.value=this.value.toUpperCase();"
    />
    <s:password
        name="password"
        key="label.password"
        required="true"
        size="25"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
    />
    <sj:submit
        id="btnSubmit"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.login"
    />
    <s:hidden name="postback" value="Y" />
</s:form>
</div>
</body>
</html>