<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="0"/>
        <title>BDSM Next Generation</title>
        <s:head theme="xhtml" />
        <sj:head jqueryui="true" loadAtOnce="true" jquerytheme="ui-lightness" />
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
            .errorMessage{
                font: bold 12px arial,sans-serif;
                color: red;
                list-style:none;
            }
            .actionMessage{
                font: bold 12px arial,sans-serif;
                color:blue;
                list-style:none;
            }
        </style>
        <script type="text/javascript" src="_js/wrapper.js"></script>
        <script type="text/javascript">
            function _resize() {
                $("#frameForm").position({
                    at: "right top",
                    my: "right top",
                    of: "#ph-right",
                    offset: "20px 20px",
                    collision: "none"
                });
                //        $(".errorMessage").position({
                //            at: "right bottom",
                //            my: "right top",
                //            of: $("#frameForm"),
                //            offset: "0px 50px",
                //            collision: "none"
                //        });
                //        $("#banner").position({
                //            at: "right bottom",
                //            my: "right bottom",
                //            of: "#ph-left",
                //            offset: "0px 0px",
                //            collision: "none"
                //        });
                $("#ph-left").width($("body").width()-Math.min($("#ph-right").width(), $("#frameForm").width()));
                $("#ph-left").outerHeight($("body").innerHeight()-$("#ph-top").outerHeight()-$("#ph-bottom").outerHeight());
                $("#ph-right").outerHeight($("body").innerHeight()-$("#ph-top").outerHeight()-$("#ph-bottom").outerHeight());
            }
    
            function selectDefTheme() {
                var theme = getCookie("bdsmTheme");
                if(theme==null || theme===undefined || theme=="") theme="ui-lightness";
        
                var link = $("#jquery_theme_link").attr("href");
                var l = link.indexOf("struts/themes/");
                var r = link.indexOf("/jquery-ui.css");
                var ls = link.substring(0, l+14);
                var rs = link.substring(r);
                $("#jquery_theme_link").attr("href", ls+theme+rs);
                $(window).resize();
                setTimeout(function() {$(window).resize();}, 1000);
                setTimeout(function() {$(window).resize();}, 10000);
            }

            $(function() {
                $("#frameForm").width("300");
                $(window).resize(_resize);
                selectDefTheme();
            })
        </script>
    </head>
    <body>
        <table class="wwFormTable" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td colspan="2" id="ph-top" class="ui-widget-header" style="padding-left:5px; width:100%; height:75px;">
                    <span style="position:absolute; float:left; z-index:0">
                        <img src='./Logo-Bank-Danamon.png' width:663px
                             style='position:absolute; left:0px; top:0px; width:330px; height:73px; z-index:0; filter:alpha(opacity=100); -moz-opacity:1.0; -khtml-opacity: 1.0; opacity: 1.0;' />
                    </span>
                    <h1 style="text-align:center; font-family:Verdana; color:#ff6600; z-index:1">
                        <!--span style="font-weight:bold; color:#006600">B</span>ank <span style="font-weight:bold; color:#006600">D</span>anamon <span style="font-weight:bold; color:#006600">S</span>upplementary <span style="font-weight:bold; color:#006600">M</span>odules-->
                        <span class="ui-widget ui-widget-header" style="font-weight:bold">B</span>ank <span class="ui-widget ui-widget-header" style="font-weight:bold">D</span>anamon <span class="ui-widget ui-widget-header" style="font-weight:bold">S</span>upplementary <span class="ui-widget ui-widget-header" style="font-weight:bold">M</span>odules
                    </h1>
                </td>
            </tr>
            <tr>
                <td id="ph-left" style="background-color:white; vertical-align:top">
                    <span style="position:relative; float:right">
                        <img src='./banner-main.jpg' id="banner"
                             style='position:absolute; right:0px; top:0px; width:995px; height:380px; z-index:0; filter:alpha(opacity=99); -moz-opacity:0.99; -khtml-opacity: 0.99; opacity: 0.99;' />
                    </span>
                </td>
                <td id="ph-right" class="ui-widget-content" style="vertical-align:top; width:300px">
                    <span style="position:relative; float:left">
                        <s:actionerror id="idError" cssStyle="position:absolute; left:0px; top:160px; width:300px; z-index:1" />
                    </span>
                    <div id="frameForm">
                        <span class="ui-widget ui-widget-header ui-corner-top" style="border:1px solid black; border-bottom:0px solid black; padding:5px; margin:0">Login Form</span>
                        <s:form id="frmLogin" action="login" method="post" focusElement="frmLogin_idUser" validate="true" namespace="/">
                            <s:textfield
                                name="idUser"
                                key="label.idUser"
                                requiredLabel="true"
                                size="25"
                                maxlength="20"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                            <s:password
                                name="password"
                                key="label.password"
                                requiredLabel="true"
                                size="25"
                                maxlength="20"
                                cssClass="ui-widget ui-widget-content"
                                autocomplete="off"
                                />
                            <sj:submit
                                id="btnSubmit"
                                buttonIcon="ui-icon-gear"
                                button="true"
                                key="button.login"
                                />
                            <s:token />
                            <s:hidden name="postback" value="Y" />
                        </s:form>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="ui-widget ui-widget-header" id="ph-bottom" style="font-size:9px; width:100%; height:20px; text-align:center">Copyright &copy; 2013 Bank Danamon Indonesia</td>
            </tr>
        </table>
    </body>
</html>