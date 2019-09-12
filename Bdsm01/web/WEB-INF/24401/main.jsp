<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	
	<s:url var="ajaxUpdateTitle" action="24401_title" />
	<s:url var="ajaxUpdateButtons" action="24401_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="24401" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Customer ID Type Inquiry --%>
	<s:form id="frmDlgIDTypeInq" action="dlg">
		<s:hidden name="dialog" value="dlgIDType" />
		<s:hidden name="master" value="idType" />
		<s:token />
	</s:form>
	<sj:a id="aDlgIDTypeInq" formIds="frmDlgIDTypeInq" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<script type="text/javascript">
		<%@include file="formValidation.js" %>
	</script>
	
	<s:form id="frmInquiry" name="frmInquiry" action="24401_execute" theme="css_xhtml">
		<fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblIdTypeInq" key="label.wic.id.id.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.idTypeInqDesc"
								size="30"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="strData.idTypeInq" />
						</td>
						<td>
							<s:label id="lblTrxTellerRefNoInq" key="label.wic.trx.print.ref.no" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.codBranch"
								size="4"
								maxlength="5"
								value="%{#session.cdBranch}"
								readonly="true"
								cssClass="ui-widget ui-widget-content cls-numeric"
							/>
							<s:textfield 
								name="strData.ctrBatchNo"
								size="3"
								maxlength="4"
								cssClass="ui-widget ui-widget-content cls-numeric"
							/>
							<s:textfield 
								name="strData.codUserNo"
								size="5"
								maxlength="6"
								cssClass="ui-widget ui-widget-content cls-numeric"
							/>
							<s:textfield
								name="strData.ctrSeqNo"
								size="5"
								maxlength="6"
								cssClass="ui-widget ui-widget-content cls-numeric"
							/>
						</td>
						<td align="right">
							<sj:submit id="btnSearch"
								formIds="frmInquiry"
								buttonIcon="ui-icon-gear"
								button="true"
								key="button.search"
								disabled="false"
								targets="ph-temp"
								onBeforeTopics="btnSearch_beforeSubmit"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblIdNumberInq" key="label.wic.id.id.number" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.idNumberInq"
								size="37"
								maxlength="40"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="this.value=this.value.toUpperCase();"
							/>
						</td>
						<td>
							<s:label id="lblDateInq" key="label.wic.trx.trx.date.inquiry" />
						</td>
						<td>
							<sj:datepicker
								id="trxDateInq"
								name="trxDateInq"
								displayFormat="dd/mm/yy"
								firstDay="1"
								maxDate="+0d"
								minDate="-1y"
								buttonImageOnly="true"
								showOn="both"
								readonly="true"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
			
			<s:hidden name="strData.customerTypeInq" value="1" /> <%-- Customer Type WIC --%>
			<s:hidden name="strData.trxRefNoInq" />
			<s:hidden name="strData.searchForType" />
			<s:token />
			<hr class="ui-widget-content" />
		</fieldset>
	</s:form>
	
	<s:form id="frmMain" name="frmMain" action="24401" theme="css_xhtml">
		<fieldset id="fsDataPersonal" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.identification.data" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblName" key="label.wic.customer.name" />
						</td>
						<td>
							<s:textfield 
								name="name"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
						<td>
							<s:label id="lblAddress" key="label.wic.customer.address" />
						</td>
						<td>
							<s:textfield 
								name="address1"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblIdType" key="label.wic.id.id.type" />
						</td>
						<td>
							<s:textfield 
								name="strData.idType"
								size="37"
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="address2"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblIdNumber" key="label.wic.id.id.number" />
						</td>
						<td>
							<s:textfield 
								name="idNumber"
								size="37"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="address3"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblIdExpiredDate" key="label.wic.id.id.expired" />
						</td>
						<td>
							<s:textfield 
								name="idExpiredDate"
								size="37"
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
						<td>
							<s:label id="lblCity" key="label.wic.customer.city" />
						</td>
						<td>
							<s:textfield 
								name="strData.city"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblBirthDate" key="label.wic.customer.birth.date" />
						</td>
						<td>
							<s:textfield 
								name="birthDate"
								size="37"
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
						<td>
							<s:label id="lblState" key="label.wic.customer.state" />
						</td>
						<td>
							<s:textfield 
								name="strData.state"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<s:label />
						</td>
						<td>
							<s:label id="lblCountry" key="label.wic.customer.country" />
						</td>
						<td>
							<s:textfield 
								name="strData.country"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsTrx" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.trx.fieldset.legend.trx" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblTrxRefNumber" key="label.wic.trx.trx.ref.no" />
						</td>
						<td>
							<s:textfield
								name="WICTrx.refNo"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
						<td>
							<s:label id="lblTrxDateTime" key="label.wic.trx.trx.datetime" />
						</td>
						<td>
							<s:textfield
								name="WICTrx.dateTime"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblTrxType" key="label.wic.trx.trx.type" />
						</td>
						<td>
							<s:textfield
								name="WICTrx.narrative"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
							<s:hidden name="WICTrx.type" />
						</td>
						<td>
							<s:label id="lblCIFNo" key="label.wic.trx.cif.no" />
						</td>
						<td>
							<s:textfield
								name="WICTrx.CIFNo"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblTrxCurrency" key="label.wic.trx.trx.currency" />
						</td>
						<td>
							<s:textfield
								name="currencyName"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
							<s:hidden name="WICTrx.currencyCode" />
						</td>
						<td>
							<s:label id="lblAcctNo" key="label.wic.trx.acct.no" />
						</td>
						<td>
							<s:textfield
								name="WICTrx.accountNo"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblTrxAmount" key="label.wic.trx.trx.amount" />
						</td>
						<td>
							<s:textfield
								name="WICTrx.amount"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
						<td>
							<s:label id="lblAcctName" key="label.wic.trx.acct.name" />
						</td>
						<td>
							<s:textfield
								name="strData.accountName"
								size="37"
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsButtons" class="ui-widget-content ui-corner-all" style="border: 0px">
			<table>
				<tbody>
					<tr>
						<td colspan="4" align="center">
							<sj:submit
							 	id="btnProcess"
							 	formIds="frmMain"
							 	buttonIcon="ui-icon-gear"
							 	button="true"
							 	targets="ph-main"
							 	key="button.save"
							 	onBeforeTopics="btnProcess_beforeSubmit"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<s:hidden name="WICTrx.trxNo" />
		<s:hidden name="WICTrx.WICNo" />
		<s:hidden name="WICTrx.fastPath" />
		<s:hidden name="WICTrx.debitOrCredit" />
		<s:hidden name="WICTrx.branch" />
		<s:hidden name="WICTrx.tellerId" />
		<s:hidden name="WICTrx.requester" />
		<s:hidden name="WICTrx.approver" />
		<s:hidden name="strDate.dtmCreated" />
		
		<s:token />
	</s:form>
	
	<div id="divMessage" title=""></div>
	
	<%-- Buttons --%>
	<sj:a id="btnLookupIdTypeInq" button="true">...</sj:a>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			/* === [BEGIN] alter display === */
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frmInquiry, #frmMain").find('.wwlbl').attr('style','width:100%;float:left;text-align:right;vertical-align:middle');
			$("#frmInquiry").find("td > div").css({'margin-left': '10px', 'margin-right': '10px'});
			$("#frmInquiry img.ui-datepicker-trigger")
				.css("margin-left", "3px");
			$("#frmMain").find("div[id^='wwgrp_lbl']").parent().width(165);
			$("#frmMain").find("tbody").children().css("height", "26px");
			$("#frmMain > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.each(function(i) {
					var width_ = ((parseInt($(this).width(), 10) + 25) + "px");
					$(this).css("width", width_);
				});;
			$("#frmMain, #frmInquiry").find("input[type='text'], textarea")
					.css("opacity", '')
					.filter("[readonly]").css("opacity", "0.5");
			
			$("#frmInquiry_strData_idTypeInqDesc").parent().append('&nbsp;').append($("#btnLookupIdTypeInq").detach());
			$("#frmInquiry_strData_codBranch").parent().append('&nbsp;&nbsp;').append($("#frmInquiry_strData_ctrBatchNo").detach());
			$("#frmInquiry_strData_codBranch").parent().append('&nbsp;&nbsp;').append($("#frmInquiry_strData_codUserNo").detach());
			$("#frmInquiry_strData_codBranch").parent().append('&nbsp;&nbsp;').append($("#frmInquiry_strData_ctrSeqNo").detach());
			
			/* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
			if (navigator.appName.indexOf("Internet Explorer") > -1) {
				$("#frmInquiry, #frmMain").find("*[role='button']").css("position", "static");
			}
			/* === [END] alter display === */
			
			
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
			
			
			$("#btnLookupIdTypeInq").click(function() {
				if (!(
						($(this).button('option').disabled != undefined) &&
						($(this).button('option', 'disabled'))
						)) {
					dlgParams = {};
					dlgParams.id = 'frmInquiry_strData_idTypeInq';
					dlgParams.desc = 'frmInquiry_strData_idTypeInqDesc';
					
					var $tmp = $("#ph-dlg").dialog("option", "title");
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
					$("#ph-dlg").dialog("option", "height", 450);
					
					$("#ph-dlg")
						.html("Please wait...")
						.unbind("dialogopen")
						.bind("dialogopen", function(){
							$("#aDlgIDTypeInq").click();
						})
						.unbind("dialogclose")
						.bind("dialogclose", function(){
							$(this).dialog({title: $tmp});
						})
						.dialog({
							title: '<s:text name="label.wic.id.id.type" />'
						})
						.dialog("open");
				}
			});
			
			
			$("#btnProcess")
				.unsubscribe("btnProcess_beforeSubmit")
				.subscribe("btnProcess_beforeSubmit", function(event) {
					$("#frmMain").unbind("submit");
					event.originalEvent.options.submit = false;
					
					dlgParams = {};
					dlgParams.idMaintainedSpv = "frmMain_WICTrx_approver";
					dlgParams.event = event;
					dlgParams.onSubmit = function() {
						// dlgParams.event.originalEvent.options.submit = true;
						$("#frmMain_requester").val('<s:property value="%{#session.idUser}" />');
						$("#btnProcess").unsubscribe("btnProcess_beforeSubmit");
						
						disableWICElements(true);
						if (rdb.current == 'delete')
							disableWICTrxElements(true);
							
						$("#btnProcess").click();
					};
					
					$("#frmMain_WICTrx_requester").val('<s:property value="%{#session.idUser}" />');
					if (rdb.current == 'delete') {
						$("#ph-dlg")
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
					else /* add */
						dlgParams.onSubmit();
				});
				
			
			var funcSearchBeforeSubmit = null;
			funcSearchBeforeSubmit = function(event) {
				event.originalEvent.options.submit = false;
				o = {};
				o.restoreEvent = function() {
					$("#btnSearch").subscribe("btnSearch_beforeSubmit", funcSearchBeforeSubmit);
					disableInquiryDescription(false);
				};
				
				var arrDate = $("#trxDateInq").val().split("/"); /* -dd/MM/yyyy- */
				var dtBatch = arrDate[2] + arrDate[1] + arrDate[0]; /* -yyyyMMdd- */ 
				var ctrBatchNo = $("#frmInquiry_strData_ctrBatchNo").val();
				var codUserNo = $("#frmInquiry_strData_codUserNo").val();
				var ctrSeqNo = $("#frmInquiry_strData_ctrSeqNo").val();
				
				$("#frmInquiry_strData_trxRefNoInq").val(codUserNo + dtBatch + Array(4 + 1 - ctrBatchNo.length).join('0') + ctrBatchNo + ctrSeqNo);
				
				if (validateForm_frmInquiry()) {
					// event.originalEvent.options.submit = true;
					$("#btnSearch").unsubscribe("btnSearch_beforeSubmit");
					disableInquiryDescription(true);
					$("#btnSearch").click();
				}
			};
			
			$("#btnSearch")
				.unsubscribe("btnSearch_beforeSubmit")
				.subscribe("btnSearch_beforeSubmit", funcSearchBeforeSubmit);
			 
			
			function disableWICElements(isDisable) {
				disableElement("frmMain_name", isDisable);
				disableElement("frmMain_strData_idType", isDisable);
				disableElement("frmMain_idNumber", isDisable);
				disableElement("frmMain_strDate_idExpiredDate", isDisable);
				disableElement("frmMain_strDate_birthDate", isDisable);
				disableElement("frmMain_address1", isDisable);
				disableElement("frmMain_address2", isDisable);
				disableElement("frmMain_address3", isDisable);
				disableElement("frmMain_strData_city", isDisable);
				disableElement("frmMain_strData_state", isDisable);
				disableElement("frmMain_strData_country", isDisable);
			}
			
			function disableWICTrxElements(isDisable) {
				disableElement("currencyName", true);
				
				disableElement("frmMain_WICTrx_WICNo", isDisable);
				disableElement("frmMain_WICTrx_refNo", isDisable);
				disableElement("frmMain_WICTrx_type", isDisable);
				disableElement("frmMain_WICTrx_currencyCode", isDisable);
				disableElement("frmMain_WICTrx_amount", isDisable);
				disableElement("frmMain_strDate_dateTime", isDisable);
				disableElement("frmMain_WICTrx_CIFNo", isDisable);
				disableElement("frmMain_WICTrx_accountNo", isDisable);
				disableElement("frmMain_strData_accountName", isDisable);
				disableElement("frmMain_WICTrx_narrative", isDisable);
				disableElement("frmMain_WICTrx_branch", isDisable);
				disableElement("frmMain_WICTrx_tellerId", isDisable);
				disableElement("frmMain_WICTrx_requester", isDisable);
				disableElement("frmMain_strDate_dtmCreated", isDisable);
			}
			
			function disableInquiryDescription(isDisable) {
				disableElement("frmInquiry_strData_idTypeInqDesc", isDisable);
				disableElement("frmInquiry_strData_ctrBatchNo", isDisable);
				disableElement("frmInquiry_strData_codUserNo", isDisable);
				disableElement("frmInquiry_strData_ctrSeqNo", isDisable);
			}
			
			function disableElement(elem, isDisable) {
				$("#" + elem).attr("disabled", (isDisable == true)? "true": null);
			}
			
			
			/* disable right click on form */
			$("form").bind("contextmenu", function(e) {
				e.preventDefault();
			});
			/* disable backspace on all element except writeable input */
			$("#frmInquiry > *, #frmMain > *")
				.unbind('keydown')
				.bind('keydown', function(e) {
					var evt = e? e: event;
					var key_ = (evt.charCode)? evt.charCode: evt.keyCode;
					keyPass = false;
					
					if ((key_ == 8) && !(('INPUT|TEXTAREA'.indexOf(evt.target.nodeName) > -1) && !(evt.target.hasAttribute('readonly')))) {
						evt.preventDefault();
					}
				});
			
			
			/* filter keys */
			var filterKeys = function(elem, func, func2) {
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
						else if ("8|37|39|46".indexOf(key_) > -1) /* backspace, left, right, delete */
							keyPass = true;
					})
					.unbind('keypress')
					.bind('keypress', function(e) {
						if (keyPass)
							return true;
						
						var charCode = (e.charCode)? e.charCode: e.keyCode;
						var ch = String.fromCharCode(charCode);
						if (func != undefined)
							func(e, $(this)[0].id, ch);
					})
					.unbind('keyup')
					.bind('keyup', function(e) {
						if (func2 != undefined)
							func2();
					});
			};
			
			filterKeys(".cls-numeric", function(e, elem_, char_) {
				if (!char_.match(/[0-9]/))
					e.preventDefault();
			},
			function() {
				
			});
			
			
			
		});
		
		var dtBusiness = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />';
	</script>
</s:if>