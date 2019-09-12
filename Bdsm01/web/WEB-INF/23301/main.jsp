<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	<script type="text/javascript" src="_js/pdf/fpdf.bundled.js"></script>
	
	
	<s:url var="ajaxUpdateTitle" action="23301_title_" />
	<s:url var="ajaxUpdateButtons" action="23301_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="23301" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<s:form id="frmFindAccountNo" action="23301_accountNoLookup.action">
		<s:hidden name="accountNo" />
		<s:token />
	</s:form>
	<sj:a id="aFindAccountNo" formIds="frmFindAccountNo" targets="ph-temp" cssClass="ui-helper-hidden" />
	
	<s:form id="frmBankLookupAutoCompleter">
		<s:hidden name="master" value="bankBillerStandingInstruction" />
		<s:hidden name="page" value="1" />
		<s:hidden name="rows" value="10" />
	</s:form>
	
	<script type="text/javascript">
		<%@include file="formValidation.js" %>
	</script>
	
	<s:form id="frmInquiry" name="frmInquiry" action="23301_execute" theme="css_xhtml">
		<fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
			<table>
				<tbody>	
					<tr id="tr">
						<td>
							<s:label id="lblSIInquiry" key="label.bpsi.label.customer.standing.instruction" requiredLabel="true" />
						</td>
						<td style="vertical-align: middle">
							<s:textfield 
								name="SI_BIC"
								size="6"
								maxlength="4"
								cssStyle="text-transform:uppercase"
								cssClass="ui-widget ui-widget-content"
								onkeyup="fieldInquiryOnKeyUp(event)"
							/>
							<s:textfield
								name="SI_CODE"
								size="38"
								maxlength="31"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase; width:100%" 
								onkeyup="fieldInquiryOnKeyUp(event)"
							/>
							<s:hidden name="id" />
						</td>
						<td style="vertical-align: middle">
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
				</tbody>
			</table>
		</fieldset>
		
		<s:hidden name="strData.mode" />
	</s:form>
	
	<s:form id="frmMain" name="frmMain" action="23301_blank" theme="css_xhtml">
		<fieldset id="fsAccount" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.bpsi.fieldset.legend.account" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblAccount" key="label.bpsi.label.customer.account" requiredLabel="true" />
						</td>
						<td colspan="3">
							<s:textfield 
								name="accountNo"
								size="50"
								maxlength="16"
								cssClass="ui-widget ui-widget-content cls-numeric"
								onblur="this.value=this.value.trim();"
							/>
							<sj:submit id="btnFindAcct"
								formIds="frmFindAccountNo"
								buttonIcon="ui-icon-gear"
								button="true"
								key="button.find"
								disabled="false"
								targets="ph-temp"
								onBeforeTopics="btnFindAcct_beforeSubmit"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblName" key="label.bpsi.label.customer.name" />
						</td>
						<td colspan="3">
							<s:textfield
								name="strData.customerName"
								readonly="true"
								cssStyle="width: 100%" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblAddress" key="label.bpsi.label.customer.address" />
						</td>
						<td colspan="3">
							<s:textfield
								name="strData.address1"
								readonly="true"
								cssStyle="width: 100%" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td />
						<td colspan="3">
							<s:textfield
								name="strData.address2"
								readonly="true"
								cssStyle="width: 100%" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td />
						<td colspan="3">
							<s:textfield
								name="strData.address3"
								readonly="true"
								cssStyle="width: 100%" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblBankName" key="label.bpsi.label.customer.bankname" />
						</td>
						<td>
							<s:textfield 
								name="strData.bankName"
								size="37"
								maxlength="40"
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblCustomerType" key="label.bpsi.label.customer.type" />
						</td>
						<td colspan="3">
							<s:textfield
								name="strData.customerType"
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblCustomerResidentialStatus" key="label.bpsi.label.customer.residentialstatus" />
						</td>
						<td colspan="3">
							<s:textfield
								name="strData.residentialStatus"
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsBiller" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.bpsi.fieldset.legend.biller" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblBillerNo" key="label.bpsi.label.biller.billingno" requiredLabel="true" />
						</td>
						<td>
							<s:textfield
								name="billingNo"
								size="50"
								maxlength="15"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblBillerName" key="label.bpsi.label.biller.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield
								name="billerName"
								cssStyle="width: 100%"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblBillerBankName" key="label.bpsi.label.biller.bankname" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlBankLookup" action="DataMaster_list" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="billerBankName"
								name="billerBankName"
								href="%{urlBankLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmBankLookupAutoCompleter"
								size="50"
								onblur="if(($('#billerBankName').val() == '') || ($('#billerBankName').val().substring(0, 4) == $('#frmMain_id').val())) {$('#billerBankName').val(''); $('#billerBankName_widget').attr('value', '');}"
								onchange="$('#billerBankName').val('');"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblBillerAccountNo" key="label.bpsi.label.biller.accountno" requiredLabel="true" />
						</td>
						<td>
							<s:textfield
								name="billerAccountNo"
								cssStyle="width: 100%"
								cssClass="ui-widget ui-widget-content cls-numeric"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsBilling" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.bpsi.fieldset.legend.billing" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblBillingPurpose" key="label.bpsi.label.billing.purpose" requiredLabel="true" />
						</td>
						<td colspan="5">
							<s:textarea
								name="billingPurpose"
								rows="6"
								cols="69"
								requiredLabel="true"
								cssStyle="resize: none"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblNominalType" key="label.bpsi.label.billing.nominal" />
						</td>
						<td>
							<s:select
								name="nominalType"
								list="#{'F':'Nilai Pembayaran Tetap', 'M':'Maximum Pembayaran Hingga'}"
								cssStyle="width:100%"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
						<td>
							<s:label id="lblRp" key="label.blank" requiredLabel="true" />
						</td>
						<td colspan="3">
							<s:textfield
								name="nominal"
								style="width: 100%"
								cssClass="ui-widget ui-widget-content cls-numeric"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblPeriod" key="label.bpsi.label.billing.period" />
						</td>
						<td>
							<s:select
								name="paymentPeriodicType"
								list="#{'M':'Bulanan', 'D':'On Demand'}"
								cssStyle="width:100%"
								cssClass="ui-widget ui-widget-content"
								onchange="selectPaymentPeriodicTypeOnChange(this);"
							/>
						</td>
						<td>
							<s:label id="lblDate" key="label.bpsi.label.billing.date" />
						</td>
						<td>
							<s:select
								name="paymentMinDate"
								list="dateList"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
						<td>
							<s:label id="lblDateTo" key="label.bpsi.label.billing.dateTo" />
						</td>
						<td>
							<s:select
								name="paymentMaxDate"
								list="dateList"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblValidity" key="label.bpsi.label.billing.validity" />
						</td>
						<td>
							<sj:datepicker
								id="frmMain_validUntil"
								name="validUntil"
								displayFormat="dd/mm/yy"
								firstDay="1"
								changeMonth="true"
								changeYear="true"
								minDate="%{new java.util.Date(#session.dtBusiness.getTime())}"
								yearRange="+0:2049"
								buttonImageOnly="true"
								showOn="both"
								readonly="true"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
						<td colspan="4" />
					</tr>
					
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsButtons" class="ui-widget-content ui-corner-all" style="border: 0px">
			<table>
				<tbody>
					<tr>
						<td colspan="4">
							<sj:submit
							 	id="btnProcess"
							 	formIds="frmMain"
							 	buttonIcon="ui-icon-gear"
							 	button="true"
							 	disabled="false"
							 	targets="ph-main"
							 	key="button.save"
							 	onBeforeTopics="btnProcess_beforeSubmit"
							/>
						</td>
						<td>
							<sj:a id="btnGeneratePDF" button="true" onclick="if ($('#btnGeneratePDF').hasClass('ui-button-disabled') == false) generatePDF();"><s:text name="button.generate.pdf" /></sj:a>							
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<s:hidden name="id" />
		<s:hidden name="flagStatus" />
		<s:hidden name="lastDebited"><s:date name="lastDebited" format="dd/MM/yyyy" /></s:hidden>
		<s:hidden name="idMaintainedSpv" />
		<s:token />
	</s:form>
	
	<div id="divMessage" title=""></div>
	
	
	<script type="text/javascript">
		function getLabelText(elementId) {
			return $("label[for='" + elementId + "']").text().replace(/[*:]/g, '').trim();
		}
		
		function getDataText(elementId, appendText) {
			var $elem = $("#" + elementId);
			var value = "";
			if ($elem.length == 1) {
				if (($elem.prop("tagName") == "INPUT") && ($elem.prop("type") == "text"))
					value = $("#" + elementId).val().trim();
				else if ($elem.prop("tagName") == "TEXTAREA")
					value = $("#" + elementId).val().trim();
				else if ($elem.prop("tagName") == "SELECT")
					value = $("#" + elementId + " option:selected").text().trim();
			}
			
			if (arguments.length > 1)
				value += (" " + appendText.trim());
				
			return (value == "")? "-": value;
		}
		
		function rgbToHex(arrRGB) {
			var result = "";
			var temp;
			
			for (i in arrRGB) {
				temp = Number(arrRGB[i]).toString(16).trim();
				if (temp.length == 1)
					temp = "0" + temp;
				
				result += temp;
			}
			
			return result;
		}
		
		var FPDRNewLegend = function(fieldsetId) {
			var obj = 
				FPDF('div')
					.text($("#" + fieldsetId + "> legend").text())
					.css({
						padding:1,
						background: rgbToHex($("#ph-title").css("border-top-color").replace(/[rgb\(\)]/g, '').split(',')),
						color: rgbToHex($("#ph-title").css("color").replace(/[rgb\(\)]/g, '').split(',')),
						fontStyle: 'bold',
						textAlign: 'center',
						borderRadius: 1.4
					});
				
			return obj;
		};
		
		var FPDFNewRow = function(labelId, dataId, appendText) {
			var isLabel = (labelId != null);
			var obj = 
				FPDF('flexbox')
					.css({fontSize: 9, lineHeight: 1.5, width: 150, marginLeft: 10})
					.append(
						FPDF('div')
							.css({width: 40})
							.text(isLabel? getLabelText(labelId): "")
					)
					.append(
						FPDF('div')
							.css({width: 5})
							.text(isLabel? ":": "")
					)
					.append(
						FPDF('div')
							.css({width: 105, fontWeight: 'bold', fontSize: 10})
							.text((arguments.length > 2)? getDataText(dataId, appendText): getDataText(dataId))
					);
				
			return obj;
		};
		
		var FPDFNewRowStatement = function (no, text) {
			var obj = 
				FPDF("flexbox")
					.css({width: 195, lineHeight: 1.3})
					.append(
						FPDF("div")
							.text(no + ".")
							.css({width: 7, textAlign: 'right'})
					)
					.append(
						FPDF("div")
							.text(text)
							.css({width: 188, paddingLeft: 2, paddingRight: 1})
					);
			
			return obj;
		};
		
		var FPDFBlankRow = function() {
			var obj = 
				FPDF('flexbox')
					.css({fontSize: 9, lineHeight: 1.5, width: 150, marginLeft: 5})
					.append(
						FPDF('div').text("")
					);
				
			return obj;
		};
		
		function generatePDF() {
			if (window.ActiveXObject || "ActiveXObject" in window) { // IE
				generatePDFContent().save("Standing_Instruction.pdf");
			}
			else {
				var newWindow = window.open('', 'Print', 'width=800, height=600');
				var frame = newWindow.document.createElement('iframe');
				
				frame.id = 'fraPrint';
				frame.width = '100%';
				frame.height = '100%';
				newWindow.document.body.appendChild(frame);
				
				frame.src = newWindow.opener.generatePDFContent().toDataUri();
			}
		}
		
		function generatePDFContent() {
			var MyDoc = FPDF.Doc.extend({
				Page: FPDF.Page.extend({
					defaultCss: {
						padding:[10,5,5,5],
						lineHeight:1.4
					},
					header: function(){
						var nowDate = new Date();
						var date = nowDate.getDate();
						var month = nowDate.getMonth() + 1;
						var year = nowDate.getFullYear();
						var hours = nowDate.getHours();
						var minutes = nowDate.getMinutes();
						var seconds = nowDate.getSeconds();
						var format2digits = function(input) {
							return (((input < 10)? "0": "") + input);
						};
						
						this.css({
								top: 5,
								fontSize: 7,
								textAlign: 'right'
							})
							.text(format2digits(date) + '-' + format2digits(month) + "-" + year + " " + 
									format2digits(hours) + ":" + format2digits(minutes) + ":" + format2digits(seconds));
					},
					footer: function(){
						this.css({
								top: 286,
								fontSize: 7,
								padding: 1,
								textAlign: 'center'
							})
							.text(function() {
								return 'PT. Bank Danamon Indonesia, Tbk.';
							});
					}
				})
			});
			
			var doc = new MyDoc({format:'a4'});
			doc.css({
			});
			
			doc.title("Standing Instruction");
			doc.author("Bank Danamon Supplementary Module (BDSM)");
			
			
			var pages = [""];
			var onePage = function(index) {
				var obj = 
					FPDF('div')
						.append(
							FPDF('flexbox')
								.css({fontSize: 7, lineHeight: 1.5, width: 20})
								.append(
									FPDF('div')
										.css({
											width: 20, 
											borderWidth: (pages[index] && (pages[index].trim() != ""))? 0.3: 0, 
											padding: 1
										})
										.text((pages[index] && (pages[index].trim() != ""))? "Untuk " + pages[index]: "")
								)
						)
						.append(
							FPDF('div')
								.text("Standing Instruction")
								.css({
									fontSize: 24,
									lineHeight: 1,
									fontStyle: 'bold',
									fontFamily: 'times',
									color: '999999',
									textAlign: 'center'
								})
						)
						.append(
							FPDF('div')
								.text("No: " + $("#frmMain_id").val())
								.css({
									fontSize: 14,
									lineHeight: 1,
									fontStyle: 'bold',
									fontFamily: 'times',
									marginBottom: 5,
									color: '999999',
									textAlign: 'center'
								})
						)
						.append(FPDRNewLegend("fsAccount"))
						.append(FPDFNewRow("lblAccount", "frmMain_accountNo"))
						.append(FPDFNewRow("lblName", "frmMain_strData_customerName"))
						.append(FPDFNewRow("lblAddress", "frmMain_strData_address1"))
						.append(FPDFNewRow(null, "frmMain_strData_address2"))
						.append(FPDFNewRow(null, "frmMain_strData_address3"))
						.append(FPDFNewRow("lblBankName", "frmMain_strData_bankName"))
						.append(FPDFNewRow("lblCustomerType", "frmMain_strData_customerType"))
						.append(FPDFNewRow("lblCustomerResidentialStatus", "frmMain_strData_residentialStatus"))
						.append(FPDFBlankRow())
						
						.append(FPDRNewLegend("fsBiller"))
						.append(FPDFNewRow("lblBillerNo", "frmMain_billingNo"))
						.append(FPDFNewRow("lblBillerName", "frmMain_billerName"))
						.append(FPDFNewRow("lblBillerBankName", "billerBankName_widget"))
						.append(FPDFNewRow("lblBillerAccountNo", "frmMain_billerAccountNo"))
						.append(FPDFBlankRow())
						
						.append(FPDRNewLegend("fsBilling"))
						.append(FPDFNewRow("lblBillingPurpose", "frmMain_billingPurpose"))
						.append(FPDFNewRow("lblNominalType", "frmMain_nominalType", "(" + getLabelText("lblRp") + " " + $("#frmMain_nominal").val() + ")"))
						.append(FPDFNewRow("lblPeriod", "frmMain_paymentPeriodicType", "(" + getLabelText("lblDate") 
									+ " " + getDataText("frmMain_paymentMinDate") 
									+ " " + getLabelText("lblDateTo") 
									+ " " + getDataText("frmMain_paymentMaxDate") + ")"))
						.append(FPDFNewRow("lblValidity", "frmMain_validUntil"))
						.append(FPDFBlankRow())
						// statement
						.append(
							FPDF('div')
								.css({
									fontSize: 7, 
									lineHeight: 1.5, 
									textAlign: 'justify', 
									borderWidth: 0.3,
									borderColor: 'ff0000', 
									padding:1, 
									marginBottom: 5,
								})
								.append(
									FPDF("text").css({paddingLeft: 1}).inner('<s:text name="label.bpsi.label.si.statement.0" />')
								)
								.append(FPDFNewRowStatement(1, '<s:text name="label.bpsi.label.si.statement.1" />'))
								.append(FPDFNewRowStatement(2, '<s:text name="label.bpsi.label.si.statement.2" />'))
								.append(FPDFNewRowStatement(3, '<s:text name="label.bpsi.label.si.statement.3" />'))
								.append(FPDFNewRowStatement(4, '<s:text name="label.bpsi.label.si.statement.4" />'))
								.append(FPDFNewRowStatement(5, '<s:text name="label.bpsi.label.si.statement.5" />'))
								.append(FPDFNewRowStatement(6, '<s:text name="label.bpsi.label.si.statement.6" />'))
								.append(FPDFNewRowStatement(7, '<s:text name="label.bpsi.label.si.statement.7" />'))
						)
						// signature
						.append(
							FPDF('flexbox')
								.css({fontSize: 9, lineHeight: 1.5, width: 195, height: 30})
								.append(
									FPDF('div')
										.css({width: 75, marginLeft: 20, textAlign: 'center'})
										.text('<s:text name="label.bpsi.label.si.signature.customer" />')
								)
								.append(
									FPDF('div')
										.css({width: 50})
								)
								.append(
									FPDF('div')
										.css({width: 70, marginLeft: 10, textAlign: 'center'})
										.text('<s:text name="label.bpsi.label.si.signature.officer" />')
								)
								.append(
									FPDF('div').css({width: 0})
								)
						)
						.append(FPDF('flexbox')
							.css({fontSize: 9, lineHeight: 1.5, width: 195, height: 0.1})
							.append(
								FPDF('div').css({width: 20})
							)
							.append(
								FPDF('div')
									.css({width: 57, borderWidth: 0.3})
									.text('')
							)
							.append(
								FPDF('div')
									.css({width: 42})
							)
							.append(
								FPDF('div')
									.css({width: 57, borderWidth: 0.3})
									.text('')
							)
							.append(
								FPDF('div').css({width: 19})
							)
						)
						.preventSplitting()
					;
				return obj;
			};
			
			for (var i=0; i<pages.length; i++) {
				doc.append(onePage(i));
			}
			
			return doc;
		}
		
		function fieldInquiryOnKeyUp(event) {
			var attr = $("#" + event.target.id).attr("readonly");
 
			if ((typeof attr != "undefined") && (attr !== false));
			else {
				event.target.value = event.target.value.toUpperCase();
				
				var bic = $("#frmInquiry_SI_BIC").val();
				var code = $("#frmInquiry_SI_CODE").val();
				$("#btnSearch").button(((bic.trim() == "") || (code.trim() == ""))? "disable": "enable");
			}
		}
		
		function selectPaymentPeriodicTypeOnChange(element) {
			if ($(element).length > 0)
				if (($(element).val() == 'D') && ($("#actionMessage").length == 0)) { // if not from after add/edit processing
					$("#frmMain_paymentMinDate").val($("#frmMain_paymentMinDate option:first-child").val());
					$("#frmMain_paymentMaxDate").val($("#frmMain_paymentMaxDate option:last-child").val());
				}
		}
		
		function fillDescriptionField() {
			$("#billerBankName_widget").attr("value", '<s:property value="%{objData.billerBankName}" />');
		}
		
		jQuery(document).ready(function() {
			/* === [BEGIN] alter display === */
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frmInquiry, #frmMain").find('.wwlbl').attr('style','width:100%;float:left;text-align:right;vertical-align:middle');
			$("#frmInquiry, #frmMain").find("div[id^='wwgrp_lbl']").parent().css({width: '165px', verticalAlign: 'middle'});
				
			$("#frmMain").find("table > tbody > tr > td:nth-child(2)").css("min-width", "160px");
			$("#frmMain").find("tbody").children().css("height", "26px");
			$("#frmMain > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("width", function() {
					var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
					return width_;
				});
			$(".wwctrl").css("display", "flex");
			
			$("#frmInquiry, #frmMain").find("input[type='text'], select").css("height", "20px");
			$("#frmInquiry, #frmMain").find("input[type='text'], textarea").css("padding-left", "3px");
			$(".ui-autocomplete-input", $("#frmMain")).addClass("ui-widget ui-widget-content");
			$("#frmMain").find("textarea").closest("td").prev()
				.css("vertical-align", "baseline")
				.find(">:first-child").css("margin-top", "5px");
			
			
			$("label[for='lblRp'], label[for='lblDate']").closest("td").css("width", "45px");
			$("label[for='lblDateTo']")
				.each(function() {
					var txt = $(this).text().trim();
					$(this).text(txt.substring(0, txt.length - 1));
				})
				.parent().css("text-align", "center")
				.closest("td").css("width", "25px");
			var $temp = $("label[for='lblRp']").first().contents().filter(function() { return this.nodeType == 3; });
			$temp.replaceWith("\tRp.:");
			
			
			$("#frmInquiry_SI_BIC").after("&nbsp;", $("#frmInquiry_SI_CODE").detach());
			$("#frmMain_accountNo").after(
				"&nbsp;", 
				$("#frmMain_accountName").detach(),
				"&nbsp;",
				$("#btnFindAcct").detach()
			);
			
			$("#frmMain_paymentMinDate").closest("td").width(40);
			$("#frmMain_nominal, #hdMaxNominal").autoNumeric("init");
			
			$("#frmMain img.ui-datepicker-trigger")
				.css("margin-left", "3px")
				.addClass("ui-helper-hidden");
			$("#frmMain_billingPurpose").closest("td").attr('style', 'vertical-align: top');
			$("#frmMain_billingPurpose")
				.attr("maximumLength", "500")
				.keypress(function(e) {
					var limit = parseInt($(this).attr("maximumLength"));
					var textLength = $(this).val().length;
					
					var evt = e? e: event;
					var key_ = (evt.charCode)? evt.charCode: evt.keyCode;
					
					if (textLength >= limit) {
						if ("8|35|36|37|39|46".indexOf(key_) == -1) /* backspace, home, end, left, right, delete */
							return false;
					}
				})
			;
			
			
			/* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
			if (navigator.appName.indexOf("Internet Explorer") > -1) {
				$("#frmInquiry, #frmMain").find("*[role='button']").css("position", "static");
				$("#ph-main > form[action$='dlg.action']").css("display", "none");
			}
			/* === [END] alter display === */
			
			
			if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				$("#rbBS").data("rdb").callCurrent();
			}
			
			
			var funcSearchBeforeSubmit = null;
			funcSearchBeforeSubmit = function(event) {
				event.originalEvent.options.submit = false;
				o = {};
				o.restoreEvent = function() {
					$("#btnSearch").subscribe("btnSearch_beforeSubmit", funcSearchBeforeSubmit);
				};
				
				$("#frmInquiry_id").val($("#frmInquiry_SI_BIC").val().trim() + $("#frmInquiry_SI_CODE").val().trim());
				
				$("#btnSearch").unsubscribe("btnSearch_beforeSubmit");
				$("#btnSearch").click();
				$("#btnSearch").button("disable");
			}; 
				
			$("#btnSearch")
				.unsubscribe("btnSearch_beforeSubmit")
				.subscribe("btnSearch_beforeSubmit", funcSearchBeforeSubmit);
			

			
			
			$("#btnFindAcct").subscribe("btnFindAcct_beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				$("#frmFindAccountNo_accountNo").attr("value", $("#frmMain_accountNo").val());
				$("#aFindAccountNo").click();
			});
			
			var fnSubmit = function(event) {
				$("#frmMain").unbind("submit");
				event.originalEvent.options.submit = false;
				
				$("#frmMain_nominal").val($("#frmMain_nominal").autoNumeric("get"));
				
				if (validateForm_frmMain()) {
					dlgParams = {};
					dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
					dlgParams.event = event;
					dlgParams.onSubmit = function() {
						$("#btnProcess").unsubscribe("btnProcess_beforeSubmit");
						
						if (rdb.current == 'delete')
							disableAccountDetailElement(true);
						
						// if impacted from status delete
						$("#frmMain").find("select").attr("disabled", null);
						$("#frmMain_validUntil").attr("disabled", null);
						 
						$("#btnProcess").click();
					};
					
					$("#ph-dlg")
						.dialog("option", "position", "center")
						.dialog("option", "width", "320")
						.dialog("option", "height", "180")
						.html("Please wait...")
						.unbind("dialogopen")
						.bind("dialogopen", function() {
							$("#aDlgAuth").click();
						})
						.unbind("dialogclose")
						.bind("dialogclose", function() {
							if ($("#frmMain_idMaintainedSpv").val() == '') {
								$.get("_js/validation_theme_css_xhtml.js");
								$("#btnProcess").subscribe("btnProcess_beforeSubmit", fnSubmit);
							}
						})
						.dialog("open");
				}
				else {
					$("#frmMain_nominal").autoNumeric("set", $("#frmMain_nominal").val());
				}
			};
					
			$("#btnProcess")
				.unsubscribe("btnProcess_beforeSubmit")
				.subscribe("btnProcess_beforeSubmit", fnSubmit);
			
			
			function disableAccountDetailElement(isDisable) {
				disableElementId("frmMain_customerName", isDisable);
				disableElementId("frmMain_address1", isDisable);
				disableElementId("frmMain_address2", isDisable);
				disableElementId("frmMain_address3", isDisable);
				disableElementId("frmMain_bankName", isDisable);
				disableElementId("frmMain_customerType", isDisable);
				disableElementId("frmMain_residentialStatus", isDisable);
				disableElementId("billerBankName_widget", isDisable);
			}
			
			function disableElementId(elemId, isDisable) {
				$("#" + elemId).attr("disabled", (isDisable == true)? "true": null);
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
			var filterKeys = function(elem, func) {
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
							func(e, $(this).val(), ch);
					});
			};
			
			filterKeys(".cls-numeric", function(e, elemText_, char_) {
				if (!char_.match(/[0-9]/))
					e.preventDefault();
			});
			
			$("#frmMain_paymentPeriodicType").val('<s:property value="%{paymentPeriodicType}" />').change();
			
			<s:if test='%{((id!=null) && (id.trim()!=""))}'>
				fillDescriptionField();
				
				if (rdb.current == 'add')
					messageBox(3, '<s:text name="label.bpsi.label.customer.standing.instruction" /> :<br><s:property value="%{id}" />', 
						function() {
							generatePDF();
							$("#rbBS").data("rdb").clear(false);
						}
					);
				else if (rdb.current == 'edit') {
					generatePDF();
					$("#rbBS").data("rdb").clear(false);
				}
				else if (rdb.current == 'delete')
					$("#rbBS").data("rdb").clear(false);
				
				
			</s:if>
			
			
		});
		
		var dtBusiness = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />';
	</script>
</s:if>
