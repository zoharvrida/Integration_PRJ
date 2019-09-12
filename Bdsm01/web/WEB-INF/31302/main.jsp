<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	<script type="text/javascript" src="_js/pdf/fpdf.bundled.js"></script>
	
	<s:url var="ajaxUpdateTitle" action="31302_title_" />
	<s:url var="ajaxUpdateButton" action="31302_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButton}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<s:form id="formData" action="31302_inquiryAcctNo.action">
		<s:hidden name="acctNoInq" />
		<s:hidden name="noPorsi" />
		<s:hidden name="hajiType" />
		<s:hidden name="strData.mode" />
		<s:token name="refTokens"/>
	</s:form>
	<sj:a id="tempformData" formIds="formData" targets="ph-temp" cssClass="ui-helper-hidden" onBeforeTopics="beforeSubmitSearch"></sj:a>
	
	<s:form id="formBalance" action="31302_inquiryBalance.action">
		<s:hidden name="acctNoInq" />
		<s:hidden name="balance" />
		<s:hidden name="codFlg" />
		<s:token name="fsTokens"/>
	</s:form>
	<sj:a id="tempformBalance" formIds="formBalance" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
	
	<s:form id="formPrint" action="31302_reqPrint.action">
		<s:hidden name="reqPrint.acctNo" />
		<s:hidden name="reqPrint.noPorsi"/>
		<s:hidden name="reqPrint.hajiType"/>
		<s:hidden name="strData.cifNo"/>
		<s:token name="printToken"/>
	</s:form>
	<sj:a id="tempformPrint" formIds="formPrint" targets="ph-temp" cssClass="ui-helper-hidden" onCompleteTopics="printTopics"></sj:a>
	
	<%-- Lookup Haji Type --%>
	<s:form id="frmDlgHajiType" action="dlg">
		<s:hidden name="dialog" value="dlgHajiType" />
		<s:hidden name="master" value="siskohatHajiType" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgHajiType" formIds="frmDlgHajiType" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="%{#session.idMenu}" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<s:form id="frmLoadNewToken" action="generateToken" cssClass="ui-helper-hidden">
		<s:hidden name="name" value="" />
		<s:token name="tokenForNewToken" />
	</s:form>
	<sj:a id="aLoadNewToken" formIds="frmLoadNewToken" targets="ph-temp" cssClass="ui-helper-hidden" />
	
	
	<s:form id="frmInquiry" name="frmInquiry" action="31302_inquiryAcctNo" theme="css_xhtml">
		<fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblAcctNoInq" key="label.skht.acct.number" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="acctNoInq"
								size="40"
								maxlength="40"
								cssClass="cls-numeric ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="this.value=this.value.toUpperCase();"
							/>
						</td>
						<td>
							<s:label id="lblStatusInq" key="label.skht.status" />
						</td>
						<td>
							<s:textfield 
								name="svdp.statusInq"
								size="20"
								maxlength="40"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								disabled="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNoPorsiInq" key="label.skht.customer.noporsi" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="noPorsi"
								size="25"
								maxlength="40"
								cssClass="cls-numeric ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="this.value=this.value.toUpperCase();"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblHajiType" key="label.skht.customer.haji.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="hajiTypeDesc"
								size="25" 
								cssClass="ui-widget ui-widget-content"
								disabled="true"
							/>
							<s:hidden name="hajiType" />
						</td>
					</tr>
				</tbody>
			</table>
			
			<s:token />
			<sj:submit
				id="btnSearchInq"
				formIds="frmInquiry"
				buttonIcon="ui-icon-gear"
				button="true"
				key="button.find"
				targets="ph-temp" 
				onBeforeTopics="beforeSubmit"
			/>
			<hr class="ui-widget-content" />
		</fieldset>
		
		<div id="divSkhtAkhir"></div>
		<div id="divSkhtPelunasanMessage"></div>
	</s:form>
	
	<s:form id="frmMain" name="frmMain" action="31302" theme="css_xhtml">
		<fieldset id="fsDataJamaah" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.skht.fieldset.legend.data.jamaah" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblNoPorsi" key="label.skht.customer.nomorporsi" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.portionNo"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblName" key="label.skht.customer.name.pelunasan" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.jamaahName"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblBirthPlace" key="label.skht.customer.birth.place" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.birthPlace"
								size="30" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblBirthDate" key="label.skht.customer.birth.date" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.datBirthCust"
								size="30" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="svdp.datBirthCust" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblHajiType" key="label.skht.customer.haji.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.hajiType"
								size="25" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="svdp.hajiType" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNik" key="label.skht.customer.nik" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.codNatId"
								size="20"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblAddress" key="label.skht.customer.address" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.txtCustAdrAdd1"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNameFather" key="label.skht.customer.binbinti" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.fatherName"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblEducationName" key="label.skht.education.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.codCustEducn"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblOccupation" key="label.skht.customer.occupation" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.occupation"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblMaritalStatus" key="label.skht.customer.marital.status" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.maritalStatus"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblSetoranAwal" key="label.skht.setoran.awal" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.setoranAwal"
								size="50"
								maxlength="105" 
								cssClass="cls-money ui-widget ui-widget-content" 
								readonly="true"
							/>
							<s:hidden name="svdp.setoranAwal" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblRekeningDebet" key="label.skht.customer.rekeningdebet" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.acctNoInq"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblRekeningKredit" key="label.skht.customer.rekeningkredit" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.acctNoTo"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsDataFinancial" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.skht.fieldset.legend.data.financial" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblNoAcct" key="label.skht.customer.noacct" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svdp.acctNo"
								size="40" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblBalance" key="label.skht.customer.balance" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.balance"
								size="40" 
								cssClass="cls-money ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="svdp.balance" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblBiayaBPIH" key="label.skht.customer.biayabpih" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.bpihInIDR"
								size="40" 
								cssClass="cls-money ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="svdp.bpihInIDR" />
						</td>
					</tr>
					<tr style="display:none">
						<td>
							<s:label id="lblUSDExchange" key="label.skht.customer.usdexchange" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.usdExchange"
								size="40" 
								cssClass="cls-money ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="svdp.usdExchange" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblSisaLunas" key="label.skht.customer.sisalunas" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.sisaLunas"
								size="40" 
								cssClass="cls-money ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="svdp.sisaLunas" />
						</td>
					</tr>
					<tr class="cls-row-button" />
					<tr class="cls-row-button">
						<td align="right" style="padding-right:5px">
							<sj:submit
								id="btnPosting"
								formIds="frmMain"
								buttonIcon="ui-icon-gear"
								button="true"
								targets="ph-main"
								key="button.posting"
								onBeforeTopics="btnPosting_beforeSubmit"
								disabled="true"
							/>
						</td>
						<td style="padding-left:5px">
							<sj:submit
								id="btnGeneratePDF"
								buttonIcon="ui-icon-gear"
								button="true"
								targets="ph-main"
								key="button.generate.pdf"
								onBeforeTopics="print_beforeSubmit"
								disabled="true"
							/>
						</td>
					</tr>
				</tbody>
			</table>
			<div id="divSkhtAkhirLoad"></div>
			<div id="divSkhtAkhirMess"></div>
		</fieldset>
		
		<s:token />
		
		<s:hidden name="state" />
		<s:hidden name="strData.idMaintainedSpv" />
		<s:hidden name="strData.cifNo" />
		
		<s:hidden name="svdp.bpsBranchCode" />
		<s:hidden name="svdp.currencyCode" />
		<s:hidden name="svdp.bpihCost" />
		<s:hidden name="svdp.embarkasi" />
		<s:hidden name="svdp.PIHKCode" />
		<s:hidden name="svdp.PIHKName" />
		<s:hidden name="svdp.flgPaidOff" />
		<s:hidden name="svdp.delayYear" />
	</s:form>
	
	<s:form id="frmPrint" name="frmPrint" action=""  theme="css_xhtml">
		<div id="divInformationJamaah" style="display:none">
			<s:hidden name="skpPrint.portionNo" />
			<s:hidden name="skpPrint.jamaahName" />
			<s:hidden name="skpPrint.fatherName" />
			<s:hidden name="skpPrint.birthPlace" />
			<s:hidden name="skpPrint.birthDate" />
			<s:hidden name="skpPrint.birthDateDesc" />
			<s:hidden name="skpPrint.address" />
			<s:hidden name="skpPrint.paidOffHIJJYear" />
			<s:hidden name="skpPrint.paidOffMasehiYear" />
			<s:hidden name="skpPrint.initialDeposit" cssClass="cls-money" />
			<s:hidden name="skpPrint.BPIHInIDR" cssClass="cls-money" />
			<s:hidden name="skpPrint.residualCost" cssClass="cls-money" />
			<s:hidden name="skpPrint.residualCostSpellingOutDesc" />
			<s:hidden name="skpPrint.paidOffHIJJYear" />
			<s:hidden name="skpPrint.paidOffMasehiYear" />
			
			
			
			<s:label id="lblNamaBank" key="label.skht.nama.bank" requiredLabel="true" />
			<s:hidden name="skpPrint.bankName" />
			
			<s:label id="lblKantorCabang" key="label.skht.kantor.cabang" requiredLabel="true" />
			<s:hidden name="skpPrint.branchName" />
			
			<s:label id="lblAlamatBank" key="label.skht.alamat.bank" requiredLabel="true" />
			<s:hidden name="skpPrint.branchAddress" />
			
			<s:label id="lblUmur" key="label.skht.customer.umur" requiredLabel="true" />
			<s:hidden name="skpPrint.ageInYear" />
			
			<s:label id="lblGender" key="label.skht.gender" requiredLabel="true" />
			<s:hidden name="skpPrint.gender" />
			<s:hidden name="skpPrint.genderDesc" />
			
			<s:label id="lblDesaName" key="label.skht.desa.name" requiredLabel="true" />
			<s:hidden name="skpPrint.villageName" />
			
			<s:label id="lblKecamatanName" key="label.skht.kecamatan.name" requiredLabel="true" />
			<s:hidden name="skpPrint.districtName" />
			
			<s:label id="lblCity" key="label.skht.customer.city" requiredLabel="true" />
			<s:hidden name="skpPrint.cityName" />
			
			<s:label id="lblState" key="label.skht.customer.state" requiredLabel="true" />
			<s:hidden name="skpPrint.provinceName" />
			
			<s:label id="lblPostalCode" key="label.skht.customer.postalcode" requiredLabel="true" />
			<s:hidden name="skpPrint.postalCode" />
			
			<s:label id="lblBiayaIDR" key="label.skht.customer.biayaidr" requiredLabel="true" />
			
			<s:label id="lblTerbilang" key="label.skht.terbilang" requiredLabel="true" />
			<s:hidden name="skpPrint.terbilang" />
			
			<s:label id="lblEmbarkasi" key="label.skht.customer.embarkasi" requiredLabel="true" />
			<s:hidden name="skpPrint.embarkasi" />
			
			<s:label id="lblKloter" key="label.skht.customer.kloter" requiredLabel="true" />
			<s:hidden name="skpPrint.kloter" />
			
			<s:label id="lblBpsBranchCode" key="label.skht.customer.bpsBranchCode" requiredLabel="true" />
			<s:hidden name="skpPrint.bpsBranchCode" />
			
			<s:label id="lblCurrencyCode" key="label.skht.customer.currencycode" requiredLabel="true" />
			<s:hidden name="skpPrint.currencyCode" />
			
			<s:label id="lblPIHKName" key="label.skht.customer.pihkname" requiredLabel="true" />
			<s:hidden name="skpPrint.PIHKName" />
			
			<s:label id="lblPIHKCode" key="label.skht.customer.pihkcode" requiredLabel="true" />
			<s:hidden name="skpPrint.PIHKCode" />
			
			<s:label id="lblFlgPaidOff" key="label.skht.customer.flgpaidoff" requiredLabel="true" />
			<s:hidden name="skpPrint.flgPaidOff" />
			
			<s:label id="lblDelayYear" key="label.skht.customer.delayyear" requiredLabel="true" />
			<s:hidden name="skpPrint.delayYear" />
		</div>
	</s:form>
	
	<%-- Buttons --%>
	<sj:a id="btnLookupHajiType" button="true">...</sj:a>
	
	
	<script type="text/javascript">
		function getLabelText(elementId) {
			return $("label[for='" + elementId + "']").text().replace(/[*:]/g, '').trim();
		}
		
		function getDataText(elementId, appendText) {
			var $elem = $("#" + elementId);
			var value = "";
			if ($elem.length == 1) {
				if (($elem.prop("tagName") == "INPUT") && (($elem.prop("type") == "text") || ($elem.prop("type") == "hidden")))
					value = $("#" + elementId).val().trim();
				else if ($elem.prop("tagName") == "TEXTAREA")
					value = $("#" + elementId).val().trim();
				else if ($elem.prop("tagName") == "SELECT")
					value = $("#" + elementId + " option:selected").text().trim();
			}
			if (arguments.length > 1)
				value += (((value.length > 0)? " ": "") + appendText.trim());
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
						padding:[1,2,1,2],
						marginBottom: 2,
						background: rgbToHex($("#ph-title").css("border-top-color").replace(/[rgb\(\)]/g, '').split(',')),
						color: rgbToHex($("#ph-title").css("color").replace(/[rgb\(\)]/g, '').split(',')),
						fontStyle: 'italic',
						borderRadius: 1.4
					});
			return obj;
		};
		
		var FPDFHeaderRow = function(labelId, dataId, appendText) {
			var isLabel = (labelId != null);
			var obj = 
				FPDF('flexbox')
					.css({fontSize: 11, lineHeight: 1.3})
					.append(
						FPDF('div')
							.css({width: 25})
							.text(isLabel? getLabelText(labelId): " ")
					)
					.append(
						FPDF('div')
							.css({width: 2})
							.text(isLabel? ":": "")
					)
					.append(
						FPDF('div')
							.css({fontWeight: 'bold', fontSize: 11, width: 50})
							.text((arguments.length > 2)? getDataText(dataId, appendText): getDataText(dataId))
					);
			return obj;
		};
		
		var FPDFNewRow = function(labelId, dataId, appendText) {
			var isLabel = (labelId != null);
			var obj = 
				FPDF('flexbox')
					.css({fontSize: 11, lineHeight: 1.5, width: 200, marginLeft: 5})
					.append(
						FPDF('div')
							.css({width: 45})
							.text(isLabel? getLabelText(labelId): " ")
					)
					.append(
						FPDF('div')
							.css({width: 5})
							.text(isLabel? ":": "")
					)
					.append(
						FPDF('div')
							.css({width: 145, fontWeight: 'bold', fontSize: 11})
							.text((arguments.length > 2)? getDataText(dataId, appendText): getDataText(dataId))
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
				generatePDF().save("SiskohatPelunasan.pdf");
			}
			else {
				var newWindow = window.open('', 'Print_' + new Date(), 'width=800, height=600');
				if (newWindow != null) {
					var frame = newWindow.document.createElement('iframe');
					frame.id = 'fraPrint';
					frame.width = '100%';
					frame.height = '100%';
					newWindow.document.body.appendChild(frame);
					frame.src = newWindow.opener.generatePDFContent().toDataUri();
				}
				else
					generatePDF().save("SiskohatPelunasan.pdf");
			}
		}
		
		function generatePDFContent() {
			var MyDoc = FPDF.Doc.extend({
				Page: FPDF.Page.extend({
					defaultCss: {
						padding:[35,15,5,15],
						lineHeight:1.4
					}
				})
			});
			var doc = new MyDoc({format:'a4'});
			doc.css({});
			doc.title("SISKOHAT");
			doc.author("Bank Danamon Supplementary Module (BDSM)");
			var pages = ["PERTAMA,  untuk Calon Jemaah Haji", "KEDUA, untuk Kantor Cabang Bank", "KETIGA,  untuk Kantor Kementerian Agama Kabupaten/Kota", "KEEMPAT, untuk Kantor Wilayah Kementerian Agama Provinsi", "KELIMA, untuk Direktorat Jenderal Penyelenggaraan Haji dan Umrah"];
			var onePage = function(index) {
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
				var arrBirth = getDataText("frmMain_svdp_birthPlace").split(",");
				var obj 
					= FPDF('div')
						.append(
							FPDF('flexbox')
								.css({fontSize: 11, marginBottom: 5})
								.append(
									FPDF('div')
										.css({width: 75})
								)
								.append(
									FPDF('div')
										.css({width: 75})
										.append(FPDFHeaderRow("lblNoPorsiInq", "frmPrint_skpPrint_portionNo")) //nomor porsi
										.append(FPDFHeaderRow("lblNamaBank", "frmPrint_skpPrint_bankName")) //nama bank
										.append(FPDFHeaderRow("lblKantorCabang", "frmPrint_skpPrint_branchName")) //kantor cabang
										.append(FPDFHeaderRow("lblAlamatBank", "frmPrint_skpPrint_branchAddress")) //alamat bank
								)
						)
						.append(
							FPDF('div')
								.text("TANDA BUKTI SETORAN PELUNASAN")
								.css({
									fontSize: 14,
									lineHeight: 1,
									fontStyle: 'bold',
									//fontFamily: 'times',
									color: '000000',
									textAlign: 'center'
								})
						)
						.append(
							FPDF('div')
								.text("BIAYA PENYELENGGARAAN IBADAH HAJI TAHUN " + $("#frmPrint_skpPrint_paidOffHIJJYear").val() + " H / " + $("#frmPrint_skpPrint_paidOffMasehiYear").val() + " M")
								.css({
									fontSize: 14,
									lineHeight: 1,
									//fontFamily: 'times',
									marginBottom: 5,
									fontStyle: 'bold',
									color: '000000',
									textAlign: 'center'
								})
						)
						
						.append(FPDFNewRow("lblName", "frmPrint_skpPrint_jamaahName")) //nama jemaah haji
						.append(FPDFNewRow("lblNameFather", "frmPrint_skpPrint_fatherName")) //bin / binti
						.append(FPDFNewRow("lblGender", "frmPrint_skpPrint_genderDesc")) //jenis kelamin
						.append(FPDFNewRow("lblBirthPlace", "frmPrint_skpPrint_birthPlace")) //tempat lahir
						.append(FPDFNewRow("lblBirthDate", "frmPrint_skpPrint_birthDateDesc")) //tanggal lahir
						.append(FPDFNewRow("lblUmur", "frmPrint_skpPrint_ageInYear")) //umur
						.append(FPDFNewRow("lblAddress", "frmPrint_skpPrint_address")) //alamat customer
						.append(FPDFNewRow("lblPostalCode", "frmPrint_skpPrint_postalCode")) //kode pos
						.append(FPDFNewRow("lblDesaName", "frmPrint_skpPrint_villageName")) //desa atau kelurahan
						.append(FPDFNewRow("lblKecamatanName", "frmPrint_skpPrint_districtName")) //kecamatan
						.append(FPDFNewRow("lblCity", "frmPrint_skpPrint_cityName")) //kabupaten atau kota
						.append(FPDFNewRow("lblState", "frmPrint_skpPrint_provinceName")) //propinsi
						.append(
							FPDF('flexbox')
								.css({fontSize: 9, lineHeight: 1.0, height: 5})
								.append(
									FPDF('div')
										.css({width: 150})
								)
						)
						.append(FPDFNewRow("lblEmbarkasi", "frmPrint_skpPrint_embarkasi")) // embarkasi
						.append(FPDFNewRow("lblSetoranAwal", "frmPrint_skpPrint_initialDeposit")) // setoran awal
						.append(FPDFNewRow("lblSisaLunas", "frmPrint_skpPrint_residualCost")) // jumlah pembayaran
						.append(FPDFNewRow("lblBiayaBPIH", "frmPrint_skpPrint_BPIHInIDR")) //biaya bpih
						.append(FPDFNewRow("lblTerbilang", "frmPrint_skpPrint_residualCostSpellingOutDesc"), "Rupiah") //terbilang
						.append(FPDFBlankRow())
						
						// more blank line
						.append(
							FPDF('flexbox')
								.css({fontSize: 9, lineHeight: 1.5, width: 195, height: 3})
								.append(
									FPDF('div')
										.css({width: 150})
								)
						)
						
						// signature
						.append(
							FPDF('div')
								.css({textAlign: 'right', fontSize: 11, lineHeight: 1.5})
								.text($("#frmPrint_skpPrint_cityName").val().replace(/KOTA/i, '').trim() + ',' + ' ' + '<s:date name="%{#session.dtBusiness}" format="dd MMMM yyyy" />')
						)
						.append(
							FPDF('flexbox')
								.css({fontSize: 11, lineHeight: 1.5})
								.append(
									FPDF('div')
										.css({textAlign: 'left'})
										.text('Disetor Oleh,')
								)
								.append(
									FPDF('div')
										.css({textAlign: 'right', padding: [0,3,0,3]})
										.text('Diterima Oleh,')
								)
						)
						.append(
							FPDF('flexbox')
								.css({fontSize: 11, lineHeight: 1.5, height: 27})
								.append(
									FPDF('div')
										.css({textAlign: 'left'})
										.text('')
								)
								.append(
									FPDF('div')
										.css({paddingTop: 10, textAlign: 'center', width: 8, height: 17, borderWidth: 0.3})
										.text ('Photo 3x4')
								)
								.append(
									FPDF('div')
										.css({textAlign: 'right'})
										.text('')
								)
						)
						.append(
							FPDF('flexbox')
								.css({lineHeight: 1.5, marginBottom: 5, fontSize: 11})
								.append(
									FPDF('div')
										.css({textAlign: 'left'})
										.text($("#frmMain_svdp_jamaahName").val())
								)
								.append(
									FPDF('div')
										.css({textAlign: 'right'})
										.text('(...............................)')
								)
						)
						.append(
							FPDF('flexbox')
								.css({fontSize: 8, lineHeight: 1.5, textAlign: 'center', width: 110, marginTop: 3})
								.append(
									FPDF('div')
										.css({ 
											borderWidth: (pages[index] && (pages[index].trim() != ""))? 0.3: 0,
											padding: 1
										})
										.text((pages[index] && (pages[index].trim() != ""))? "LEMBAR " + pages[index]: "")
								)
						)
						.preventSplitting();
				return obj;
			};
			for (var i=0; i<pages.length; i++) {
				doc.append(onePage(i));
			}
			return doc;
		}
		
		jQuery(document).ready(function() {
			/* === [BEGIN] alter display === */
			
			$wtg = $("div[role='dialog']").find("div[id='divSkhtAkhir']");
			$wtg.dialog("close");
			$wtg.dialog("destroy");
			$wtg.remove();
			
			$wtg = $("div[role='dialog']").find("div[id='divSkhtAkhirLoad']");
			$wtg.dialog("close");
			$wtg.dialog("destroy");
			$wtg.remove();
			
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frmInquiry, #frmMain").find('.wwlbl').attr('style','width:100%;float:left;text-align:right;vertical-align:middle');
			$("#frmInquiry, #frmMain").find("div[id^='wwgrp_lbl']").parent().width(165);
			$("#frmInquiry_acctNoInq").parent().append('&nbsp;').append($("#btnSearchInq").detach());
			$("#frmInquiry_hajiTypeDesc").parent().append('&nbsp;').append($("#btnLookupHajiType").detach());
			$("#frmMain").find("table > tbody > tr > td:nth-child(2)").css("min-width", "260px");
			$("#frmMain").find("tbody").children().css("height", "26px");
			$("#frmMain > fieldset > legend")
			.css("margin-left", "10px")
			.css("text-align", "center")
			.css("width", function() {
				var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
				return width_;
			});
			$(".ui-autocomplete-input", $("#frmMain")).addClass("ui-widget ui-widget-content");
			
			$("#frmInquiry_svdp_statusInq").attr("id", "frmMain_svdp_statusInq"); // change id attribute of statusInq
			
			var autoNumericIDR = {aSign: 'Rp. ', aSep: '.', aDec: ','};
			var autoNumericUSD = {aSign: ' US$', pSign: 's'};
			$("#frmMain_strData_setoranAwal").autoNumeric("init", autoNumericIDR);
			$("#frmMain_strData_balance").autoNumeric("init", autoNumericIDR);
			//$("#frmMain_strData_bpihCost").autoNumeric("init", autoNumericUSD);
			$("#frmMain_strData_bpihInIDR").autoNumeric("init", autoNumericIDR);
			$("#frmMain_strData_usdExchange").autoNumeric("init", autoNumericIDR);
			$("#frmMain_strData_sisaLunas").autoNumeric("init", autoNumericIDR);
			
			$("#frmPrint_skpPrint_initialDeposit").autoNumeric("init", autoNumericIDR);
			$("#frmPrint_skpPrint_residualCost").autoNumeric("init", autoNumericIDR);
			$("#frmPrint_skpPrint_BPIHInIDR").autoNumeric("init", autoNumericIDR);
			
			filterKeysNumeric();
			
			//tittle 31302
			if ($("#frmMain_state").val() == 0) {
				// //(($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				$("#rbBS").data("rdb").callCurrent();
					
				if ($("#frmMain_state").val() > "0") {
					<s:if test="%{#request.SKHT_MESSAGE.equals('') == false}">
						messageBoxClass(3, "divSkhtAkhirMess", '<s:property value="%{#request.SKHT_MESSAGE}" />');
					</s:if>
					
					if ($("#frmMain_state").val() == "2")
						$("#btnGeneratePDF").button("enable");
				}
			}
			
			var dtBusiness = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />';
		});
		
		function myDialog(eButton, title, eDialog, eId, eDesc, closeFunction) {
			return function() {
				if (!(
					(eButton.button('option').disabled != undefined) &&
						(eButton.button('option', 'disabled'))
				)) {
					dlgParams = {};
					dlgParams.id = eId;
					dlgParams.desc = eDesc;
						
					var $tmp = $("#ph-dlg").dialog("option", "title");
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
					$("#ph-dlg").dialog("option", "height", 450);
						
					$("#ph-dlg")
						.html("Please wait...")
						.unbind("dialogopen")
						.bind("dialogopen", function(){
							$("#" + eDialog).click();
						})
						.unbind("dialogclose")
						.bind("dialogclose", function(){
							$(this).dialog({title: $tmp});
							if (closeFunction != undefined)
								closeFunction();
						})
						.dialog({
							title: title
						})
						.dialog("open");
				}
			};
		};
			
		$("#btnLookupHajiType").click(myDialog(
			$(this), '<s:text name="label.skht.customer.haji.type" />', 'aDlgHajiType', 
			'frmInquiry_hajiType', 'frmInquiry_hajiTypeDesc' 
		));
		
		
		$("#btnGeneratePDF")
			.unsubscribe("print_beforeSubmit")
			.subscribe("print_beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				var sessionFlag = '<s:property value="%{#session.SKHT}"/>';
				if (($("#frmMain_state").val() == '2') && (sessionFlag == "CREATED")) {
					$('#formPrint_reqPrint_acctNo').attr('value',$('#frmMain_svdp_acctNoInq').val());
					$('#formPrint_reqPrint_noPorsi').attr('value',$('#frmMain_svdp_portionNo').val());
					$('#formPrint_reqPrint_hajiType').attr('value',$('#frmMain_svdp_hajiType').val());
					$('#formPrint_strData_cifNo').attr('value', $('#frmMain_strData_cifNo').val());
					var messaging = "Please Wait Your Request Being Processed..";
					waitingMessage(3,messaging,"divSkhtAkhirLoad");
					$("#tempformPrint").click();
						
					tempParams = {};
					tempParams.onClose = function(skpPrint, strData, sessionFlag, state) {
						$("div[role='dialog']").find("div[id='divSkhtAkhirLoad']")
							.dialog("close")
							.dialog("destroy");
						
						$("#btnGeneratePDF").button("disable");
						
						if ((skpPrint != "") && (state == "3")) {
							for(var propt in skpPrint) {
								var $e = $("#frmPrint_skpPrint_" + propt);
								if ($e.hasClass("cls-money"))
									$("#frmPrint_skpPrint_" + propt).autoNumeric("set", skpPrint[propt]);
								else
									$("#frmPrint_skpPrint_" + propt).val(skpPrint[propt]);;
							}
							
							if ($.isEmptyObject(strData) == false) {
								for (var i in strData) {
									var $e = $("#frmPrint_skpPrint_" + i + "Desc");
									if ($e.hasClass("cls-money"))
										$e.autoNumeric('set', strData[i]);
									else
										$e.val(strData[i]);
								}
							}
								
							generatePDF();
						}
						else if (skpPrint == "") {
							alert("JSON failed");
						}
					}
				}
				else {
					messageBoxClass(1, "divSkhtAkhirMess",'Print Failed, press OK to close menu', function(){$("#ph-main").scrollTop(0);});
				}
			});
		
		$("#btnSearchInq").subscribe("beforeSubmit", function(event) {
			event.originalEvent.options.submit = false; 
			var noRep = $.trim($('#frmInquiry_acctNoInq').val());
			var noPor = $.trim($('#frmInquiry_noPorsi').val());
			var noHaj = $.trim($('#frmInquiry_hajiType').val());
			
			if (noRep == '') {
				messageBoxClass(1, "divSkhtAkhirMess",'Must Fill No Account, No Porsi, and Haji Type', function(){$("#ph-main").scrollTop(0);});
				$('#frmInquiry_acctNoInq').focus();
				return;
			}
			else if (noPor == '') {
				messageBoxClass(1, "divSkhtAkhirMess",'Must Fill No Porsi', function(){$("#ph-main").scrollTop(0);});
				$('#frmInquiry_noPorsi').focus();
				return;
			}
			else if (noHaj == '') {
				messageBoxClass(1, "divSkhtAkhirMess",'Must Fill Haji Type', function(){$("#ph-main").scrollTop(0);});
				$('#frmInquiry_hajiTypeDesc').focus();
				return;
			}
			else {
				$('#formData_acctNoInq').attr('value', $('#frmInquiry_acctNoInq').val());
				$('#formData_noPorsi').attr('value', $('#frmInquiry_noPorsi').val());
				$('#formData_hajiType').attr('value', $('#frmInquiry_hajiType').val());
				$('#formData_strData.mode').attr('value', $("#formAcctNo_strData_mode").val(rdb.current));
				$('#formData_codFlg').attr('value', "I");
				$('#tempformData').click();
				var messaging = "Please Waiting Your Request . . . . .";
				waitingMessage(3,messaging,"divSkhtAkhir");
				
				inqParam = {};
				inqParam.onClose = function(svdp, sessionFlag, message) {
					if (message != '') {
						$("div[role='dialog']").find("div[id='divSkhtAkhir']")
							.dialog("close")
							.dialog("destroy");
						
						messageBoxClass(2, "divSkhtPelunasanMessage", message);
					}
					else if (svdp != '') {
						if (svdp.errorCode==null) {
							for(var propt in svdp) {
								$("#frmMain_svdp_" + propt).val(svdp[propt]);
							}
							
							if (rdb.current == "add") {
								$('#formBalance_acctNoInq').attr('value', $('#frmMain_svdp_acctNo').val());
								$('#formBalance_balance').attr('value', $('#frmMain_svdp_sisaLunas').val());
								$('#formData_codFlg').attr('value', "I");
								$("#tempformBalance").click();
								
								tempParam = {};
								tempParam.onClose = function(balance, sessionFlag) {
									$("div[role='dialog']").find("div[id='divSkhtAkhir']")
										.dialog("close")
										.dialog("destroy");
									if (balance != "") {
										if (balance === "VALIDATE") {
											/*$("#tempformData").unsubscribe("beforeSubmitPay");
												$("#tempformData").click();*/
											$("#btnPosting").button("enable");
										}
										else {
											messageBoxClass(1, "divSkhtPelunasanMessage","Insufficient for Balance", function(event) {
												$("#frmGo_idMenu").val(idMenu);
												$("#frmGo_goAction").val(control);
											});
											$("#btnPosting").button("disable");
										}
									}
								}
							}
							else {
								$("div[role='dialog']").find("div[id='divSkhtAkhir']")
									.dialog("close")
									.dialog("destroy");
							}
							
							$("#btnGeneratePDF").button("disable");
						}
						else {
							var idMenu = "31302";
							var control = "inquiry";
							
							messageBoxClass(1, "divSkhtPelunasanMessage", svdp.errorCode, function(event) {
								$("#frmGo_idMenu").val(idMenu);
								$("#frmGo_goAction").val(control);
								$("#btnGo").click();
							});
						}
					}
					else {
						var idMenu = "31302";
						var control = "inquiry";
						messageBoxClass(1, "divSkhtPelunasanMessage",(message=='')? "Failed to Inquiry Data": message, function(event) {
							$("#frmGo_idMenu").val(idMenu);
							$("#frmGo_goAction").val(control);
							$("#btnGo").click();
						});
					}
				}
			};
		});
		
		$("#btnPosting")
			.unsubscribe("btnPosting_beforeSubmit")
			.subscribe("btnPosting_beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				dlgParams = {};
				dlgParams.idMaintainedSpv = "frmMain_strData_idMaintainedSpv";
				dlgParams.event = event;
				dlgParams.onSubmit = function() {
					objCallback = {};
					objCallback.function_ = function() {
						$("#formBalance").find("input[name='fsTokens']").val($("#frmTokenGenerator_hdToken").val());
						
						var messaging = "Please Waiting Your Request . . . . .";
						waitingMessage(3, messaging, "divSkhtAkhirLoad");
						
						$('#formBalance_acctNoInq').attr('value', $('#frmMain_svdp_acctNo').val());
						$('#formBalance_balance').attr('value', $('#frmMain_svdp_sisaLunas').val());
						$('#formData_codFlg').attr('value', "P");
						$("#tempformBalance").click();						
					}
					$("#frmLoadNewToken").find("input[name='name']").val("fsTokens");
					$("#aLoadNewToken").click();
					
					
					tempParam = {};
					tempParam.onClose = function(balance, sessionFlag) {
						if (balance != "") {
							if (balance === "VALIDATE") {
								$("#frmMain").find(".cls-money").each(function(item) {
									$(this).val($(this).autoNumeric('get'));
								});
								
								$("#btnPosting").unsubscribe("btnPosting_beforeSubmit");
								$("#btnPosting").click();
								// alert("Click Posting");
								// var messaging = "Please Wait . . . . .";
								// waitingMessage(3, messaging, "divSkhtAkhir");
								$("#btnGeneratePDF").button("enable");
							}
							else {
								messageBoxClass(1, "divSkhtPelunasanMessage", "Insufficient for Balance", function(event) {
									$("#frmGo_idMenu").val(idMenu);
									$("#frmGo_goAction").val(control);
								});
							}
						}
					}
				}
				
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
						}
					})
					.dialog("open");
				
				var sessionFlag = $("#frmMain_sessions").val();
		});
	</script>
</s:if>