<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <sj:head jqueryui="true" loadAtOnce="true" jquerytheme="flick" />
        <link href="perfect-scrollbar.css" rel="stylesheet">
        <link href="jquery.switchButton.css" rel="stylesheet">
        <script src="jquery.mousewheel.js"></script>
        <script src="perfect-scrollbar.js"></script>        
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
                font-family:"Arial", "Helvetica", "Verdana", "sans-serif";
                font-size:11px;
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
            span.wwlbl {
                display:inline-block;
                width:150px;
                text-align:right;
            }
            span.wwctrl{
                vertical-align: top;
            }
            .col1of2 {
                width:400px;
            }
            .col1of3 {
                width:250px;
            }
            fieldset {
                margin-top:5px;
                margin-bottom:5px;
            }
            .bdsm-combobox {
                position: relative;
                display: inline-block;
            }
            .bdsm-combobox-toggle {
                position: absolute;
                top: 0;
                bottom: 0;
                margin-left: -1px;
                padding: 0;
            }
            .bdsm-combobox-input {
                /*margin: 0;
                padding: 5px 10px;*/
                width: 30px;
            }
            #divmenu
            {
                height:inherit; 
                overflow:scroll; 
            }
            #box2000 > div
            {
                -webkit-transition: width 0.2s ease; 
                -moz-transition: width 0.2s ease; 
                -o-transition: width 0.2s ease; 
                -ms-transition: width 0.2s ease;     
                transition: width 0.2s ease; 
            }
            #box2000 > div:hover
            {
                width:120%!important;
                cursor:pointer;
            }
        </style>
        <script type="text/javascript" src="_js/wrapper.js"></script>
        <script type="text/javascript" src="_js/jquery.formatCurrency-1.4.0.js"></script>
        <script type="text/javascript" src="_js/jquery.nicescroll.js"></script>
        <script type="text/javascript" src="_js/autoNumeric.js"></script>
        <script type="text/javascript" src="_js/jquery.switchButton.js"></script>
        <script type="text/javascript" src="_js/jquery.bgiframe.js"></script> <!-- fix overlay component over jquery ui dialog  -->
        
        <script type="text/javascript">
            function _resize() {
                $("#ph-error").dialog("option", "width", $("#ph-main").width());
                $("#ph-error").dialog("option", "height", $("#ph-main").height() / 2);

                //$("#ph-dlg0").dialog("option", "height", $("#ph-main").height());
                //$("#ph-dlg0").dialog("option", "width", $("#ph-main").width());

                $("#ph-frame").width($("body").width() - $("#ph-menu").width());

                $("#ph-frame").outerHeight($("body").innerHeight() - $("#ph-top").outerHeight() - $("#ph-bottom").outerHeight());
                $("#ph-main").outerHeight($("body").innerHeight() - $("#ph-top").outerHeight() - $("#ph-bottom").outerHeight() - $("#ph-title").outerHeight() - $("#ph-buttons").outerHeight() - 22);
                $("#ph-menu").outerHeight($("body").innerHeight() - $("#ph-top").outerHeight() - $("#ph-bottom").outerHeight());
                //$("#ph-menu-child").height($("body").innerHeight() - $("#ph-top").outerHeight() - $("#ph-bottom").outerHeight());

                /*alert("Height:" +
                 "\nbody: " + $("body").height() +
                 "\nph-menu: " + $("#ph-menu").height() +
                 "\nph-top: " + $("#ph-top").height() +
                 "\nph-bottom: " + $("#ph-bottom").height() +
                 "\nph-frame: " + $("#ph-frame").height() +
                 "\nph-title: " + $("#ph-title").height() +
                 "\nph-main: " + $("#ph-main").height() +
                 "\nph-buttons: " + $("#ph-buttons").height() +
                 ""
                 );
                 alert("Outer Height:" +
                 "\nbody: " + $("body").outerHeight() +
                 "\nph-menu: " + $("#ph-menu").outerHeight() +
                 "\nph-top: " + $("#ph-top").outerHeight() +
                 "\nph-bottom: " + $("#ph-bottom").outerHeight() +
                 "\nph-frame: " + $("#ph-frame").outerHeight() +
                 "\nph-title: " + $("#ph-title").outerHeight() +
                 "\nph-main: " + $("#ph-main").outerHeight() +
                 "\nph-buttons: " + $("#ph-buttons").outerHeight() +
                 ""
                 );
                 alert("Inner Height:" +
                 "\nbody: " + $("body").innerHeight() +
                 "\nph-menu: " + $("#ph-menu").innerHeight() +
                 "\nph-top: " + $("#ph-top").innerHeight() +
                 "\nph-bottom: " + $("#ph-bottom").innerHeight() +
                 "\nph-frame: " + $("#ph-frame").innerHeight() +
                 "\nph-title: " + $("#ph-title").innerHeight() +
                 "\nph-main: " + $("#ph-main").innerHeight() +
                 "\nph-buttons: " + $("#ph-buttons").innerHeight() +
                 ""
                 );*/
            }

            function selectTheme(val) {
                var link = $("#jquery_theme_link").attr("href");
                var l = link.indexOf("struts/themes/");
                var r = link.indexOf("/jquery-ui.css");
                var ls = link.substring(0, l + 14);
                var rs = link.substring(r);
                $("#jquery_theme_link").attr("href", ls + val + rs);
                $(window).resize();
                setTimeout(function() {
                    $(window).resize();
                }, 1000);
                setTimeout(function() {
                    $(window).resize();
                }, 10000);
                setCookie("bdsmTheme", val, 30);
            }

            function selectDefTheme() {
                var theme = getCookie("bdsmTheme");
                if (theme == null || theme === undefined || theme == "")
                    theme = "ui-lightness";
                $("#theme").val(theme);
                $("#theme").change();
                $(window).resize();
                setTimeout(function() {
                    $(window).resize();
                }, 1000);
                setTimeout(function() {
                    $(window).resize();
                }, 10000);
            }

            $(function() {
                $("#theme").combobox({size: 10});
                $("#divmenu").niceScroll({autohidemode: true})
                $(window).resize(_resize);
                $("#ph-error").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false,
                    buttons: {
                        "OK": function() {
                            $(this).dialog("close");
                        }
                    }
                });
                $("#ph-dlg").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false
                });
                selectDefTheme();

                $("#frmGo_idMenu").attr("accesskey", "M");
                $("#frmGo_idMenu")
                        .parent()
                        .append(
                                $("#btnGo").detach()
                                );

                $("#btnGo").click(function() {
                    $("#ph-main").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:40px; height:40px; z-index:99' /></span>" + $("#ph-main").html());
                    $("#ph-title").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:20px; height:20px; z-index:99' /></span>" + $("#ph-title").html());
                    $("#ph-buttons").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:20px; height:20px; z-index:99' /></span>" + $("#ph-buttons").html());
                });
                $('#ph-menu div').perfectScrollbar({
                    wheelSpeed: 20,
                    wheelPropagation: false
                });
            });

            function bodyOnLoad() {
            }
            function bodyOnUnload() {
            }
        </script>
    </head>

    <body onload="bodyOnLoad()" onunload="bodyOnUnload()">
        <table class="wwFormTable" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="2" id="ph-top" class="ui-widget-header" style="padding-left:5px; width:100%; height:55px;">
                    <img src="./Logo-Bank-Danamon.png" style="position:absolute; left:0; top:0; width:220px; height:55px; z-index:2" />
                    <div style="position:absolute;left:220px; top:0; margin-left:5px ">
                        <div style="font-weight:normal">
                            User: <s:property value="%{#session.idUser}" /> |
                            Date: <s:date name="%{#session.dtBusiness}" format="d-MMM-yyyy" /> |
                            Branch: <s:property value="%{#session.cdBranch}" /> |
                            Template: <s:property value="%{#session.namTemplate}" /> |
                            <s:a id="logout" action="logout">Logout</s:a>
                            </div>
                            <div style="font-weight:normal">
                            <s:form id="frmGo" action="menu_click">
                                <s:textfield name="idMenu"
                                             size="6"
                                             maxlength="5"
                                             cssClass="ui-widget ui-widget-content" 
                                             />
                                <s:hidden name="noCif" />
                                <s:hidden name="noAcct" />
                                <s:hidden name="refTxn" />
                                <s:hidden name="typMsg" />
                                <s:hidden name="goAction" />
								<s:hidden name="typAcct" />
                                <s:hidden name="typTrx" />
                                <s:hidden name="datTxnCOM" />
                                <s:hidden name="statusDescCOM" />
                                <s:hidden name="statusCOM" />
                                <sj:submit id="btnGo" formIds="frmGo" value="Go" targets="ph-main" button="true" />
                                <s:token />
                            </s:form>
                        </div>
                    </div>
                    <div style="font-weight:normal; float:right; margin-right:30px">
                        <label for="theme" class="label">Theme: </label>
                        <select id="theme" onchange="selectTheme(this.value);" class="ui-widget ui-widget-content">
                            <option value="cupertino"     >Aqua      </option>
                            <option value="excite-bike"   >Blue Spot </option>
                            <option value="redmond"       >Blue      </option>
                            <option value="start"         >Blue2     </option>
                            <option value="humanity"      >Brown     </option>
                            <option value="sunny"         >Brown2    </option>
                            <option value="black-tie"     >Contrast  </option>
                            <option value="hot-sneaks"    >Dots      </option>
                            <option value="flick"         >Grey      </option>
                            <option value="overcast"      >Grey2     </option>
                            <option value="smoothness"    >Grey3     </option>
                            <option value="south-street"  >Green Spot</option>
                            <option value="ui-lightness"  >Orange    </option>
                            <option value="blitzer"       >Red       </option>
                            <option value="pepper-grinder">White     </option>

                            <!--option value="black-tie">Black Tie</option>
                            <option value="blitzer">Blitzer</option>
                            <option value="cupertino">Cupertino</option>
                            <option value="dark-hive">Dark Hive</option>
                            <option value="dot-luv">Dot Luv</option>
                            <option value="eggplant">Egg Plant</option>
                            <option value="excite-bike">Excite Bike</option>
                            <option value="flick">Flick</option>
                            <option value="hot-sneaks">Hot Sneaks</option>
                            <option value="humanity">Humanity</option>
                            <option value="le-frog">Le Frog</option>
                            <option value="mint-choc">Mint Choc</option>
                            <option value="overcast">Overcast</option>
                            <option value="pepper-grinder">Pepper Grinder</option>
                            <option value="redmond">Redmond</option>
                            <option value="smoothness">Smoothness</option>
                            <option value="south-street">South Street</option>
                            <option value="start">Start</option>
                            <option value="sunny">Sunny</option>
                            <option value="swanky-purse">Swanky Purse</option>
                            <option value="trontastic">Trontastic</option>
                            <option value="ui-darkness">UI Darkness</option>
                            <option value="ui-lightness">UI Lightness</option>
                            <option value="vader">Vader</option>-->
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <td id="ph-menu" class="ui-widget-content" style="width:350px; text-align:left; vertical-align:top; border: 0px solid white">
                    <s:url var="ajaxMenu" action="menu_input" />
                    <sj:div id="divmenu" href="%{ajaxMenu}">Loading...</sj:div>
                </td>
                <td id="ph-frame" class="ui-widget-content" style="vertical-align:top; border: 0px solid white">
                    <div id="ph-title" class="ui-widget ui-widget-header ui-corner-top" style="text-align:left; vertical-align:baseline; margin-top:10px; margin-right:10px; padding:5px; overflow:auto">
                        &nbsp;
                    </div>
                    <div id="ph-main" class="ui-widget-content" style="overflow:auto; margin-right:10px; padding:5px; overflow:auto">
                    </div>
                    <div id="ph-buttons" class="ui-widget ui-widget-header ui-corner-bottom" style="text-align:left; vertical-align:baseline; margin-bottom:10px; margin-right:10px; padding:5px; overflow:auto">
                        &nbsp;
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="ui-widget ui-widget-header" id="ph-bottom" style="font-size:9px; width:100%; height:20px; text-align:center">Copyright &copy; 2013 Bank Danamon Indonesia</td>
            </tr>
        </table>

        <div id="ph-error" title="Error" style="display:none"></div>
        <div id="ph-dlg" title="Dialog" style="display:none;"></div>
        <div id="ph-temp" title="Temp" style="display:none"></div>
    </body>
</html>
