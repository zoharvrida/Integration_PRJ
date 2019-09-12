<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>

    <s:url var="ajaxUpdateTitle" action="29401_title_" />
    <s:url var="ajaxUpdateButtons" action="29401_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <s:form id="formCalc" action="29401_getCalc.action">
        <s:hidden name="amtTxn" />
        <s:hidden name="codCcy1" />
        <s:hidden name="usdEquivalent" />
        <s:token name="calcToken"/>
    </s:form>
    <sj:a id="tempformCalc" formIds="formCalc" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

    <s:form id="formRate" action="29401_rtvRate.action">
        <s:hidden name="currencyCode" />
        <s:hidden name="sellrate" />
        <s:token name="rateToken" />
    </s:form>
    <sj:a id="tempformRate" formIds="formRate" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

    <%-- Lookup 1. Original Currency --%>
    <s:form id="frmDlgOriCcyInq" action="dlg">
        <s:hidden name="dialog" value="dlgOriCcy" />
        <s:hidden name="master" value="originalCurrency" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgOriCcyInq" formIds="frmDlgOriCcyInq" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <%-- Lookup Currency Code Inquiry (destination) --%>
    <s:form id="frmDlgCCodeRateInq" action="dlg">
        <s:hidden name="dialog" value="dlgCCodeRate" />
        <s:hidden name="master" value="multiCurrencyRemittance" />
        <s:hidden name="term"   value=""/>
        <s:token />
    </s:form>
    <sj:a id="aDlgCCodeRateInq" formIds="frmDlgCCodeRateInq" targets="ph-dlg" cssClass="ui-helper-hidden" />



    <s:form id="frmMain" name="frmMain" action="29401_execute" theme="css_xhtml">
        <fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblOriCcyInq" key="label.mcr.original.currency" requiredLabel="true"/>
                        </td>
                        <td>
                            <s:textfield
                                name="namCurrencyDesc" 
                                size="20"                            
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                />
                            <s:hidden name="namCurrency" />
                        </td>
                        <td>&nbsp;</td>
                    </tr>              
                    <tr>
                        <td>
                            <s:label id="lblOriAmount" key="label.mcr.original.amount" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield
                                name="oriAmount" 
                                size="20"
                                maxlength="100"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                />
                            <s:hidden name="oriAmount" />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblUsdEquival" key="label.mcr.usd.equivalent" requiredLabel="false" />
                        </td>
                        <td>
                            <s:textfield 
                                name="usdEquival"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                disabled="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDestCcyNamInq" key="label.mcr.destination.currencyname" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield
                                name="currencyCode" 
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                />
                            <s:hidden name="FlagCode" />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDestCcyRate" key="label.mcr.destination.currencyrate" requiredLabel="false" />
                        </td>
                        <td>
                            <s:textfield 
                                name="usdDestinationRate"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                disabled="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDestAmount" key="label.mcr.destination.amount" requiredLabel="false" />
                        </td>
                        <td>
                            <s:textfield 
                                name="destAmount"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                disabled="true"
                                />
                        </td>
                    <tr>
                        <td>
                            <sj:a id="btnCalculate" button="true">Calculate</sj:a>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>                      
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td></tr>
                </tbody>
            </table>
            <fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
                <table>
                    <tbody>
                        <tr>
                            <td>
                                *This calculation is only temporary indicative. To know the nominal                      
                            </td>
                        </tr>
                        <tr>
                            <td>
                                amount of transaction please input transaction on transaction calculator.                       
                            </td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
            <s:token />
            <hr class="ui-widget-content" />
        </fieldset>
            <s:hidden name="equivalentusd" />
    </s:form>
    <%-- Buttons --%>
    <sj:a id="btnLookupOriCcyInq" button="true">...</sj:a>
    <sj:a id="btnLookupDestCcyInq" button="true">...</sj:a>
	<sj:a id="usd" button="false">USD</sj:a> 
    <sj:a id="usdDestCcyRate" button="false">USD/</sj:a>
    
    
	
    

    <script type="text/javascript">
        jQuery(document).ready(function() {
            /* === [BEGIN] alter display === */
            $("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
        
                
            $("#frmMain_namCurrencyDesc").parent().append('&nbsp;').append($("#btnLookupOriCcyInq").detach());
            $("#frmMain_currencyCode").parent().append('&nbsp;').append($("#btnLookupDestCcyInq").detach());
            $("#frmMain_usdEquival").parent().append('&nbsp;').append($("#usd").detach());
            $("#frmMain_usdDestinationRate").parent().append('&nbsp;').append($("#usdDestCcyRate").detach());
			$("#btnCalculate").hide();
                
            //tittle 29401
            if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
                $("#rbBS").buttonset("destroy");
                $("#tempTitle").click();
                $("#tempButtons").click();
            }
            else {
                $("#rbBS").data("rdb").callCurrent();
                if ($("#actionError").length == 0)
                    $("#rbBS").data("rdb").clear(false);
            }
                
            //
            $("#frmMain_so_multiCurrencyRemittance").attr("disabled","true");
            $("#frmMain_so_multiCurrencyRemittance").val(null);
                
            function calcUSDEquivalent() {
                $('#formCalc_amtTxn').attr('value', $("#frmMain_oriAmount").val());
                $('#formCalc_codCcy1').attr('value', $("#frmMain_namCurrencyDesc").val());
                $('#formCalc_usdEquivalent').attr('value', $("#frmMain_usdEquival").val());
                $("#tempformCalc").click();
                calcParams = {};
                calcParams.amtTxn = "frmMain_oriAmount";
                calcParams.codCcy1 = "frmMain_namCurrencyDesc";
                calcParams.usdEquival = "frmMain_usdEquival";
                calcParams.onClose = function(amtTxn, codCcy1, usdEquival) {
            
                    $("#frmMain_oriAmount").attr("value",amtTxn);
                    $("#frmMain_oriAmount").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                    $("#frmMain_namCurrencyDesc").attr("value",codCcy1);
                    $("#frmMain_usdEquival").attr("value",usdEquival);
                    $("#frmMain_equivalentusd").attr("value",usdEquival);
                    $("#frmMain_usdEquival").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                } 
            }
        
            function rateDes() {
                $('#formRate_currencyCode').attr('value', $("#frmMain_currencyCode").val());
                $('#formRate_sellRate').attr('value', $("#frmMain_usdDestinationRate").val());
                $("#tempformRate").click();
                rateParams = {};
                rateParams.currencyCode = "frmMain_currencyCode";
                rateParams.sellRate = "frmMain_usdDestinationRate";
                rateParams.onClose = function(currencyCode,sellRate) {
					if (sellRate == ''){
						alert("This Currency No Have Rate");
					}else{
						$("#frmMain_currencyCode").attr("value",currencyCode);
						$("#frmMain_usdDestinationRate").attr("value",sellRate);
						$('#usdDestCcyRate').html($('#usdDestCcyRate').html() + $("#frmMain_currencyCode").val());
						$("#btnCalculate").show();
					}
                } 
            }
            
            function amountDestination() {
                var usdequiv = $("#frmMain_equivalentusd").val();
                var rate     = $("#frmMain_usdDestinationRate").val();
                var amount   = usdequiv*rate;
                
                //alert("amount :" + amount + "rate :" + rate + "usdequivalent :" + usdequiv);
				
				if (rate == '' || usdequiv == ''){
					alert("Rate can't empty for calculate")
				}else{
					$("#frmMain_destAmount").val(amount);
					$("#frmMain_destAmount").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
				}
            }
                
            //
            $("#btnLookupOriCcyInq").click(function() {
                if (!(
                ($(this).button('option').disabled != undefined) &&
                    ($(this).button('option', 'disabled'))
            )) {
                    dlgParams = {};
                    dlgParams.id = 'frmMain_namCurrencyDesc';
                    dlgParams.desc = 'frmMain_namCurrency';
					
                    var $tmp = $("#ph-dlg").dialog("option", "title");
                    $("#ph-dlg").dialog("option", "position", "center");
                    $("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
                    $("#ph-dlg").dialog("option", "height", 450);
					
                    $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function(){
                        $("#frmDlgOriCcyInq_term").attr("value", $('#frmMain_namCurrencyDesc').val());
                        $("#aDlgOriCcyInq").click();
                    })
                    .unbind("dialogclose")
                    .bind("dialogclose", function(){
                        $(this).dialog({title: $tmp});
                    })
                    .dialog({
                        title: '<s:text name="label.mcr.original.currency" />'
                    })
                    .dialog("open");
                }
            });
                
            //
            $("#btnLookupDestCcyInq").click(function() {
                if (!(
                ($(this).button('option').disabled != undefined) &&
                    ($(this).button('option', 'disabled'))
            )) {
                    dlgParams = {};
                    dlgParams.id = 'frmMain_currencyCode';
                    dlgParams.desc = 'frmMain_FlagCode';
					dlgParams.onClose = function(){
						var cur = $.trim($("#frmMain_currencyCode").val());
						if (cur == ''){
							alert("Please select currency");
							$("#frmMain_currencyCode").focus();
							return;
						}else{
							rateDes();
						}
                    }
                    var $tmp = $("#ph-dlg").dialog("option", "title");
                    $("#ph-dlg").dialog("option", "position", "center");
                    $("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
                    $("#ph-dlg").dialog("option", "height", 450);
					
                    $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function(){
                        $("#frmDlgCCodeRateInq_term").attr("value", $('#frmMain_currencyCode').val());
                        $("#aDlgCCodeRateInq").click();
                    })
                    .unbind("dialogclose")
                    .bind("dialogclose", function(){
                        $(this).dialog({title: $tmp});                   
                    })
                    .dialog({
                        title: '<s:text name="label.mcr.destination.currencyname" />'
                    })
                    .dialog("open");
                }
            });
        
            $("#frmMain_oriAmount").unbind('keydown');
            $("#frmMain_oriAmount").bind('keydown', function(e) {
                if(e.keyCode == 9) {
                    calcUSDEquivalent();
                }
            });
        
            $("#frmMain_currencyCode").unbind('keydown');
            $("#frmMain_currencyCode").bind('keydown', function(e) {
                if(e.keyCode == 9) {
                    rateDes();
					$("#btnCalculate").show();
                }
            });
            
             $("#btnCalculate").click(function(){
                 amountDestination();
                   
            });
       
        });        
                
    </script>
</s:if>