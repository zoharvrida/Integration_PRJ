<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	
	<script type="text/javascript">
		<%@include file="formValidation.js" %>
	</script>
	
	<s:url var="ajaxUpdateTitle" action="27401_title_" />
	<s:url var="ajaxUpdateButtons" action="27401_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg" cssClass="ui-helper-hidden">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="%{#session.idMenu}" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Account Searching --%>
	<s:form id="frmFindAcct" action="27401_execute.action" cssClass="ui-helper-hidden">
		<s:hidden name="noAccount" />
		<s:hidden name="mode" />
		<s:token name="tokenFindAcct" />
	</s:form>
	<sj:a id="aFindAcct" formIds="frmFindAcct" targets="ph-temp" cssClass="ui-helper-hidden" onSuccessTopics="postValidateAcctNo" />
	
	<%-- Product MIS --%>
	<s:form id="frmDlgProductMIS" action="dlg" cssClass="ui-helper-hidden">
		<s:hidden name="dialog" value="dlgMPProductMIS" />
		<s:hidden name="master" value="MitraPastiProductMIS" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgProductMIS" formIds="frmDlgProductMIS" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Classification --%>
	<s:form id="frmDlgClassification" action="dlg" cssClass="ui-helper-hidden">
		<s:hidden name="dialog" value="dlgMPClassification" />
		<s:hidden name="master" value="MitraPastiClassification" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgClassification" formIds="frmDlgClassification" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	
	<s:form id="frmLoadNewToken" action="27401Tab_execute" cssClass="ui-helper-hidden">
		<s:hidden name="name" value="" />
		<s:token name="tokenForNewToken" />
	</s:form>
	<sj:a id="aLoadNewToken" formIds="frmLoadNewToken" targets="ph-temp" cssClass="ui-helper-hidden" />
	
	<%-- Components based on classification --%>
	<s:form id="frmLoadComponent" action="27401_loadTabComponent.action" cssClass="ui-helper-hidden">
		<s:hidden name="id" />
		<s:hidden name="codeClass" />
		<s:token name="token" />
	</s:form>
	<sj:a id="aLoadComponent" formIds="frmLoadComponent" targets="divComponent" cssClass="ui-helper-hidden" />
	
	
	<s:form id="frmMain" name="frmMain" action="27401"  theme="css_xhtml" focusElement="frmMain_acctNo" validate="false">
		<fieldset id="fsInquiry" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.mitrapasti.fieldset.legend.account" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblAccountNo" key="label.noAcct" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="noAccount" 
								requiredLabel="true" 
								size="20"
								maxlength="16" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
						<td>
							<s:label id="lblTxtAccountName" label="" />
						</td>
						<td>
							<sj:submit 
								id="btnFind" 
								formIds="frmMain" 
								buttonIcon="ui-icon-gear"
								button="true" 
								key="button.find" 
								disabled="false" 
								targets="ph-main"
								onBeforeTopics="beforeSubmit" 
							/>							
						</td>
					</tr>
					<tr class="clsAcctInformation">
						<td>
							<s:label id="lblProduct" key="label.mitrapasti.product" />
						</td>
						<td>
							<s:label id="lblTxtProductCode" label="" />
						</td>
						<td colspan="2">
							<s:label id="lblTxtProductName" label="" />
						</td>
					</tr>
					<tr class="clsAcctInformation">
						<td>
							<s:label id="lblProductMIS" key="label.mitrapasti.product.mis" />
						</td>
						<td>
							<s:label id="lblTxtProductMISCode" label="" />
						</td>
						<td colspan="2">
							<s:label id="lblTxtProductMISName" label="" />
						</td>
					</tr>
					<tr class="clsAcctInformation">
						<td>
							<s:label id="lblProductMIS" key="label.mitrapasti.classification" />
						</td>
						<td colspan="3">
							<s:label id="lblTxtClassification" label="" />
						</td>
					</tr>
					<tr class="clsAcctInformation">
						<td>
							<s:label id="lblProductMIS" key="label.mitrapasti.commitment.date" />
						</td>
						<td colspan="3">
							<s:label id="lblTxtDateCommitment" label="" />
							<sj:datepicker
								id="frmMain_dateCommitment" 
								name="dateCommitment"
								displayFormat="dd/mm/yy"
								firstDay="1"
								minDate="%{new java.util.Date(#session.dtBusiness.getTime())}"
								changeMonth="false"
								changeYear="false"
								buttonImageOnly="true"
								showOn="button"
								readonly="true"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
					</tr>
				</tbody>
			</table>
			
			<s:hidden name="id" />
			<s:hidden name="idOld" disabled="true" />
			<s:hidden name="codeClass" />
			<s:hidden name="codeClassOld" disabled="true" />
			<s:hidden name="hdFlagResult" />
			<s:hidden name="hdFlagChangePackage" value="0" disabled="true" />
			<s:token />
		</fieldset>
	</s:form>
	
	<%-- Buttons --%>
	<sj:a id="btnLookupClassification" button="true">...</sj:a>
	<sj:a id="btnLookupProductMIS" button="true">...</sj:a>
	
	<br>
	<div id="divComponent"></div>
	<div id="divMessage"></div>
	<br>
	
	<sj:submit
		id="btnProcess"
		formIds="frmComponent"
		buttonIcon="ui-icon-gear"
		button="true"
		key="button.save"
		targets="ph-main"
		onBeforeTopics="btnProcess_beforeSubmit"
	/>
	
	<table>
		<tr><td>&nbsp;</td></tr>
	</table>
	

	<script type="text/javascript">
		$(function() {
			/* === [BEGIN] alter display === */
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$(".label[for^='lblTxt']").text("");
			$("#frmMain img.ui-datepicker-trigger").css("margin-left", "4px");
			$("#frmMain > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("width", function() {
					var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
					return width_;
				});
			
			/* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
			if (navigator.appName.indexOf("Internet Explorer") > -1) {
				$("body").find("*[role='button']").css("position", "static");
			}
			/* === [END] alter display === */
			
			
			setIdLabelFor("lblTxtAccountName");
			setIdLabelFor("lblTxtProductCode");
			setIdLabelFor("lblTxtProductName");
			setIdLabelFor("lblTxtProductMISCode");
			setIdLabelFor("lblTxtProductMISName");
			setIdLabelFor("lblTxtClassification");
			setIdLabelFor("lblTxtDateCommitment");
			
			$('#lblTxtClassification').parent().append('&nbsp;').append($("#btnLookupClassification").detach());
			$('#lblTxtProductMISCode').parent().append('&nbsp;').append($("#btnLookupProductMIS").detach());
			
			
			if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				$("#rbBS").data("rdb").callCurrent();
				if ($("#actionError").length == 0)
					$("#rbBS").data("rdb").clear(false);
			}
			
			
			$("#btnFind").subscribe("beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				var acctNo = $.trim($("#frmMain_noAccount").val());
				
				if (acctNo == '') {
					messageBox(2, "<s:text name='error.account.mandatory' />");
					$("#frmMain_noAccount").focus();
					
					return;
				}
				else {
					tmpObj = {};
					tmpObj.hdFlagResult = "frmMain_hdFlagResult";
					tmpObj.accountName = "lblTxtAccountName";
					tmpObj.productCode = "lblTxtProductCode";
					tmpObj.productName = "lblTxtProductName";
					tmpObj.productMISCode = "lblTxtProductMISCode";
					tmpObj.productMISName = "lblTxtProductMISName";
					tmpObj.dateCommitment = "lblTxtDateCommitment";
					
					$("#frmFindAcct_noAccount").val($("#frmMain_noAccount").val());
					$("#frmFindAcct_mode").val((rdb.current == "add")? "1": "0");
					$("#aFindAcct").click();
				}
			});
			
			
			function onChangePackage() {
				$("#divComponent").empty();
				$("#btnProcess").hide();
				$("#frmMain_hdFlagChangePackage").attr("value", "1");
				
				var dd, mm;
				var strBussDate = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />';
				var strCommtDate = $("#frmMain_dateCommitment").attr("value");
				var tmpDate = dateTextToDate(isAfterDate(strBussDate, strCommtDate)? strBussDate: strCommtDate);
				
				/* Set to first date of next month */
				tmpDate.setDate(1);
				tmpDate.setMonth(tmpDate.getMonth() + 1);
				
				dd = tmpDate.getDate();
				mm = tmpDate.getMonth() + 1;
				
				if (dd < 10) dd = "0" + dd;
				if (mm < 10) mm = "0" + mm;
				 
				$("#frmMain_dateCommitment").attr("value", dd + "/" + mm + "/" + tmpDate.getFullYear());
			}
			
			$("#btnLookupProductMIS").click(function(){
				if (!(($(this).button('option').disabled != undefined) && ($(this).button('option', 'disabled')))) {
				
					function openProductMISDialog() {
						$("#frmDlgProductMIS_term").val($("#lblTxtProductCode").text());
					
						dlgParams = {};
						dlgParams.id = 'lblTxtProductMISCode';
						dlgParams.desc = 'lblTxtProductMISName';
						dlgParams.onClose = function() {
							$("#" + dlgParams.id).text($("#" + dlgParams.id).val());
							$("#" + dlgParams.desc).text($("#" + dlgParams.desc).val());
							
							$("#frmMain_codeClass").val("");
							$("#lblTxtClassification").text("");
							
							$("#divComponent").empty();
							$("#btnProcess").hide();
							
						};
						
						var $tmp = $("#ph-dlg").dialog("option", "title");
						$("#ph-dlg").dialog("option", "position", "center");
						$("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
						$("#ph-dlg").dialog("option", "height", 450);
						
						$("#ph-dlg")
							.html("Please wait...")
							.unbind("dialogclose")
							.dialog({
								title: '<s:text name="label.mitrapasti.product.mis" />',
								close: function(event, ui) { 
											$(this).dialog({title: $tmp});
										}
							})
							.unbind("dialogopen")
							.bind("dialogopen", function() {
								$("#aDlgProductMIS").click();
							})
							.dialog("open");
					}
					
					if ((rdb.current == 'edit') && ($("#frmMain_hdFlagChangePackage").attr("value") == "0")) {
						$("#frmMain_id").val(""); // reset id if on edit mode
						messageBoxOkCancel(
							1, 
							"Are you sure to change the package?",
							function() {
								onChangePackage();
								openProductMISDialog();
							},
							function() {
								return false;
							}
						);
					}
					else
						openProductMISDialog();
				}
			});
			
			
			$("#btnLookupClassification").click(function(){
				if (!(($(this).button('option').disabled != undefined) && ($(this).button('option', 'disabled')))) {
					function openClassificationDialog() {
						$("#frmDlgClassification_term").val($("#lblTxtProductCode").text() + $("#lblTxtProductMISCode").text());
						
						makeDlgParamsClassification();
						
						var $tmp = $("#ph-dlg").dialog("option", "title");
						$("#ph-dlg").dialog("option", "position", "center");
						$("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
						$("#ph-dlg").dialog("option", "height", 450);
						
						$("#ph-dlg")
							.html("Please wait...")
							.unbind("dialogclose")
							.dialog({
								title: '<s:text name="label.mitrapasti.classification" />',
								close: function(event, ui) { 
											$(this).dialog({title: $tmp}); 
										}
							})
							.unbind("dialogopen")
							.bind("dialogopen", function() {
								$("#aDlgClassification").click();
							})
							.dialog("open");
					}
					
					if ((rdb.current == 'edit') && ($("#frmMain_hdFlagChangePackage").attr("value") == "0")) {
						$("#frmMain_id").val(""); // reset id if on edit mode
						messageBoxOkCancel(
							1, 
							"Are you sure to change the package?",
							function() {
								onChangePackage();
								openClassificationDialog();
							},
							function() {
								return false;
							}
						);
					}
					else
						openClassificationDialog();
				}
			});
		
		
			$("#btnProcess")
				.unsubscribe("btnProcess_beforeSubmit")
				.subscribe("btnProcess_beforeSubmit", function(event) {
					event.originalEvent.options.submit = false;
					
					/* preventing blank fields */
					$("#frmComponent").find("input[type!='hidden'][name^='strData']").each(function(index) {
						var parent = $(this).closest("td");
						var value = parent.prev().html().trim();
						
						if ($(this).val().trim() == "")
							$(this).val(value);
					});
					
					if (validateForm_frmComponent()) {
						dlgParams = {};
						dlgParams.idMaintainedSpv = "frmComponent_idMaintainedSpv";
						dlgParams.event = event;
						dlgParams.onSubmit = function() {
							if (rdb.current == "delete") {
								/* disable all element except id and token */
								$("#frmComponent").find("input").each(function(index) {
									var name = $(this).attr("name");
									if ((name !== undefined) && (name != "id") && (name.indexOf("token") == -1))
										$(this).attr("disabled", true);
								});
							}
							else if ((rdb.current == "add") || (rdb.current == "edit")) {
								/* unformatting money value */
								$("#frmComponent").find(".compMoney").each(function(index) {
									$(this).val($(this).autoNumeric("get"));
									$(this).autoNumeric("destroy");
								});
								
								/* disable all checkbox element, because they will be not sent to server */
								$("#frmComponent").find("input[name*='chkComp']").attr("disabled", true);
								
								/* if change Mitra Pasti package */
								if ((rdb.current == "edit") && ($("#frmMain_codeClass").val().trim() != $("#frmMain_codeClassOld").val().trim())) {
									$("#frmComponent_id").val($("#frmMain_idOld").val()); // restore id value
									$("#frmComponent_mode").attr("disabled", null);
								}
							}
							
							$("#btnProcess")
								.unsubscribe("btnProcess_beforeSubmit")
								.click();
						};
						
						$("#ph-dlg")
							.dialog({
								title: '<s:text name="button.auth" />',
								close: function() { $.get("_js/validation_theme_css_xhtml.js"); }
							})
							.dialog("option", "position", "center")
							.dialog("option", "width", "320")
							.dialog("option", "height", "180")
							.html("Please wait...")
							.unbind("dialogopen")
							.bind("dialogopen", function() {
								$("#aDlgAuth").click();
							})
							.dialog("open");
					}
				});
		});
		
		function makeDlgParamsClassification() {
			dlgParams = {};
			dlgParams.id = 'frmMain_codeClass';
			dlgParams.desc = 'lblTxtClassification';
			dlgParams.onClose = function() {
				$("#" + dlgParams.desc).text($("#" + dlgParams.desc).val());
				
				objCallback = {};
				objCallback.function_ = function() {
					codeClassOnChange($("#frmTokenGenerator_hdToken").val());
				};
				
				$("#aLoadNewToken").click();
			};
		}
		
		function codeClassOnChange(token) {
			$("#frmLoadComponent_id").val($("#frmMain_id").val());
			$("#frmLoadComponent_codeClass").val($("#frmMain_codeClass").val());
			$("#frmLoadComponent").find("input[name='token']").val(token);
			$("#aLoadComponent").click();
		}
		
		function setIdLabelFor(labelFor) {
			$obj = $(".label[for^='" + labelFor + "']");
			if ($obj.length == 1)
				$obj.attr("id", labelFor);
		}
		
		
		/* filter keys */
		var filterKeys = function(elem, func, funcOnTab) {
			var keyPass = false;
			
			$(elem)
				.unbind('keydown')
				.bind('keydown', function(e) {
					var evt = e? e: event;
					var key_ = (evt.charCode)? evt.charCode: evt.keyCode;
					keyPass = false;
					
					if (evt.ctrlKey) {
						if (key_ != 45) { /* only receive insert when press ctrl*/
							evt.preventDefault();
						}
						keyPass = true; 
					}
					else if (evt.shiftKey && (key_ == 45)) /* shift insert */
						keyPass = true;
					else if ("|8|37|39|46|".indexOf("|" + key_+ "|") > -1) /* backspace, left, right, delete */
						keyPass = true;
					else if (key_ == 9)
						if (funcOnTab != undefined)
							funcOnTab();
						
				})
				.unbind('keypress')
				.bind('keypress', function(e) {
					if (keyPass)
						return true;
					
					var charCode = (e.charCode)? e.charCode: e.keyCode;
					var ch = String.fromCharCode(charCode);
					if (func != undefined)
						func(e, $(this).val(), ch);
				});
		};
			
		filterKeys("#frmMain_noAccount", 
			function(e, elemText_, char_) {
				if (!char_.match(/[0-9]/))
					e.preventDefault();
			},
			function() {
				$("#btnFind").click();
			});
			
		
		dlgParams = {};
		makeDlgParamsClassification();
	</script>
		
</s:if>