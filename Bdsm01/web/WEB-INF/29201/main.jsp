<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>

    <s:url var="ajaxUpdateTitle" action="29201_title_" />
    <s:url var="ajaxUpdateButtons" action="29201_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <%-- Lookup Currency Code Inquiry --%>
    <s:form id="frmDlgCCodeRateInq" action="dlg">
        <s:hidden name="dialog" value="dlgCCodeRate" />
        <s:hidden name="master" value="multiCurrencyRemittance" />
        <s:hidden name="term"   value=""/>
        <s:token />
    </s:form>
    <sj:a id="aDlgCCodeRateInq" formIds="frmDlgCCodeRateInq" targets="ph-dlg" cssClass="ui-helper-hidden" />


    <s:form id="frmMain" name="frmMain" action="29201_execute" theme="css_xhtml">
        <fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblCurrCodeInq" key="label.mcr.curency.code" />
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
                        <td align="right">
                            <sj:submit 
                                id="btnSearch" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.search" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeSubmit" 
                                />
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <s:label id="lblValDateInq" key="label.mcr.value.date"/>
                        </td>
                        <td>    
                            <sj:datepicker
                                id="valueDate"
                                name="valueDate"
                                displayFormat="dd/mm/yy"
                                firstDay="1"
                                maxDate="+0d"
                                minDate="-1y"
                                buttonImageOnly="true"
                                showOn="both"
                                readonly="false"
                                cssClass="ui-widget ui-widget-content" 
                                />
                        </td>

                        <td>&nbsp;</td>
                    </tr>
                </tbody>
            </table>
            <s:token />
            <hr class="ui-widget-content" />
        </fieldset>
    </s:form>
    <%-- Buttons --%>
    <sj:a id="btnLookupCurrCodeInq" button="true">...</sj:a>

    <%-- TabbedPanel for Grid --%>
    <s:url var="urlTab" action="29201Tab_input">
        <param name="currencyCode" value="" />
        <param name="valueDate" value="" />
    </s:url>
    <sj:tabbedpanel id="tabbedPanel" disabled="true">
        <sj:tab id="tabCurrency" href="%{urlTab}" label="Currency Rate" />
    </sj:tabbedpanel>    

    <script type="text/javascript">
        jQuery(document).ready(function() {
            /* === [BEGIN] alter display === */
			$('#tabbedPanel').hide(); 
			$('#tabbedPanel ul').attr('style', 'display:none');
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */    
			$("#frmMain_currencyCode").parent().append('&nbsp;').append($("#btnLookupCurrCodeInq").detach());
			$("#frmMain_so_multiCurrencyRemittance").attr("disabled","true");
			$("#frmMain_so_multiCurrencyRemittance").val(null);
			
				//tittle 29201
			if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
                tmpObj = {};
				tmpObj.FlagCode = "frmMain_FlagCode";
				tmpObj.currencyCode = "frmMain_currencyCode";
                $('#tabbedPanel').tabs('url', 0, '29201Tab_execute');
				$('#tabbedPanel').tabs({
					ajaxOptions :{
						type: 'POST',
						data: {
							'currencyCode': $("#frmMain_FlagCode").val(),
							'valueDate': $("#valueDate").val('<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />')
						}
					}						
				});
				$('#tabbedPanel').tabs('load', 0);
				$('#tabbedPanel').show();
				$("#tempButtons").click();
			}else {
				$("#rbBS").data("rdb").callCurrent();
				if ($("#actionError").length == 0)
					$("#rbBS").data("rdb").clear(false);
			}
			
			//Picklist currency code
			$("#btnLookupCurrCodeInq").click(function() {
				if (!(
				($(this).button('option').disabled != undefined) &&
					($(this).button('option', 'disabled'))
			)) {
					dlgParams = {};
					dlgParams.id = 'frmMain_FlagCode';
					dlgParams.desc = 'frmMain_currencyCode';
						
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
						title: '<s:text name="label.mcr.curency.code" />'
					})
					.dialog("open");
				}
			});
			
			fnLoadData = function() {
				tmpObj = {};
				tmpObj.FlagCode = "frmMain_FlagCode";
				tmpObj.currencyCode = "frmMain_currencyCode";
				
				$('#tabbedPanel').tabs('url', 0, '29201Tab_execute');
				$('#tabbedPanel').tabs({
					ajaxOptions :{
						type: 'POST',
						data: {
							'currencyCode': $("#frmMain_FlagCode").val(),
							'valueDate': $("#valueDate").val()
						}
					}
				});
				$('#tabbedPanel').tabs('load', 0);
				$('#tabbedPanel').show();
			};
			
			$("#btnSearch").subscribe("beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				fnLoadData();
				$("#btnSearch").button("disable");
			});
        });
		
		var dtBusiness = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />'; 
    </script>
</s:if>