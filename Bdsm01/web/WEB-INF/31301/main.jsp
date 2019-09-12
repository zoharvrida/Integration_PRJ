<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	<script type="text/javascript" src="_js/pdf/fpdf.bundled.js"></script>
	
	<s:url var="ajaxUpdateTitle" action="31301_title_" />
	<s:url var="ajaxUpdateButtons" action="31301_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="%{#session.idMenu}" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<s:form id="formReset" action="31301_sessionReset.action">
		<s:hidden name="sessiond" />
		<s:token name="sessionToken" />
	</s:form>
	<sj:a id="tempformReset" formIds="formReset" targets="ph-temp" cssClass="ui-helper-hidden" />
	
	<s:form id="frmAcctNo" action="31301_inquiryAcctNo.action">
		<s:hidden name="acctNoInq" />
		<s:hidden name='namCustFull' />
		<s:hidden name='codNatId' />
		<s:hidden name="strData.mode" />
		<s:token name="refToken" />
	</s:form>
	<sj:a id="aAcctNoInquiry" formIds="frmAcctNo" targets="ph-main" cssClass="ui-helper-hidden" />
	
	<s:form id="frmRequestPrint" action="31301_reqPrint.action">
		<s:hidden name="reqPrint.acctNo" />
		<s:hidden name="reqPrint.validationNo" />
		<s:hidden name="reqPrint.hajiType" />
		<s:hidden name="strData.cifNo" />
		<s:token name="printToken"/>
	</s:form>
	<sj:a id="aRequestPrint" formIds="frmRequestPrint" targets="ph-temp" cssClass="ui-helper-hidden" />
	
	<s:form id="frmBalance" action="31301_inquiryBalance.action">
		<s:hidden name="acctNoInq" />
		<s:hidden name="setoranAwal" />
		<s:token name="fsTokens"/>
	</s:form>
	<sj:a id="aAcctBalanceInquiry" formIds="frmBalance" targets="ph-temp" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Province/State --%>
	<s:form id="frmDlgState" action="dlg">
		<s:hidden name="dialog" value="dlgState" />
		<s:hidden name="master" value="siskohatState" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgState" formIds="frmDlgState" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup City --%>
	<s:form id="frmDlgCity" action="dlg">
		<s:hidden name="dialog" value="dlgCity" />
		<s:hidden name="master" value="siskohatCity" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgCity" formIds="frmDlgCity" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Occupation --%>
	<s:form id="frmDlgOccupation" action="dlg">
		<s:hidden name="dialog" value="dlgOccupation" />
		<s:hidden name="master" value="siskohatOccupation" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgOccupation" formIds="frmDlgOccupation" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Marital Status Type --%>
	<s:form id="frmDlgMaritalStatus" action="dlg">
		<s:hidden name="dialog" value="dlgMaritalStatus" />
		<s:hidden name="master" value="siskohatMaritalStatus" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgMaritalStatus" formIds="frmDlgMaritalStatus" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Education --%>
	<s:form id="frmDlgEducationName" action="dlg">
		<s:hidden name="dialog" value="dlgEducationName" />
		<s:hidden name="master" value="siskohatEducation" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgEducationName" formIds="frmDlgEducationName" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Haji Type --%>
	<s:form id="frmDlgHajiType" action="dlg">
		<s:hidden name="dialog" value="dlgHajiType" />
		<s:hidden name="master" value="siskohatHajiType" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgHajiType" formIds="frmDlgHajiType" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	
	<script type="text/javascript">
		<%@include file="formValidation.js" %>
	</script>
	
	<s:form id="frmInquiry" name="frmInquiry" action="31301_execute" theme="css_xhtml">
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
								size="37"
								maxlength="16"
								cssClass="cls-numeric ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
							/>
						</td>
						<td>
							<s:label id="lblStatusInq" key="label.skht.status" />
						</td>
						<td>
							<s:textfield 
								name="svd.statusInq"
								size="20"
								maxlength="40"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								disabled="true"
							/>
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
		<div id="divSkhtAwal"></div>
		<div id="divSkhtAwalMess"></div>
	</s:form>
	
	<s:form id="frmMain" name="frmMain" action="31301" theme="css_xhtml">
		<fieldset id="fsDataJamaah" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.skht.fieldset.legend.data.jamaah" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblNoValidasi" key="label.skht.no.validasi" />
						</td>
						<td>
							<s:textfield 
								name="noValidasi"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNamaBank" key="label.skht.nama.bank" />
						</td>
						<td>
							<s:textfield 
								name="namaBank"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblKantorCabang" key="label.skht.kantor.cabang" />
						</td>
						<td>
							<s:textfield 
								name="kantorCabang"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblAlamatBank" key="label.skht.alamat.bank" />
						</td>
						<td>
							<s:textfield 
								name="alamatBank"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblKodePIHK" key="label.skht.kode.pihk" />
						</td>
						<td>
							<s:textfield 
								name="kodePIHK"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNamaPIHK" key="label.skht.nama.pihk" />
						</td>
						<td>
							<s:textfield 
								name="namaPIHK"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNoRegist" key="label.skht.no.regist" />
						</td>
						<td>
							<s:textfield 
								name="noRegist"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNoRekening" key="label.skht.no.rekening" />
						</td>
						<td>
							<s:textfield 
								name="noRekening"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblName" key="label.skht.customer.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.namCustFull"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNameFather" key="label.skht.customer.father.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.fatherName"
								size="50"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
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
								name="svd.birthPlace"
								size="30" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
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
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.datBirthCust" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblGender" key="label.skht.gender" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.txtCustSex"
								size="30" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.codCustSex" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblHajiType" key="label.skht.customer.haji.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="objData.hajiId"
								size="8" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								disabled="false"
							/>
							<s:hidden name="hajiType" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblNik" key="label.skht.customer.nik" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.codNatId"
								size="20" 
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
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
								name="svd.txtCustAdrAdd1"
								size="50"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblPostalCode" key="label.skht.customer.postalcode" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.txtCustAdrZip"
								size="6" 
								maxlength="5"
								cssClass="cls-numeric ui-widget ui-widget-content" 
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblDesaName" key="label.skht.desa.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.desaName"
								size="40" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblKecamatanName" key="label.skht.kecamatan.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.kecamatanName"
								size="40" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblState" key="label.skht.customer.state" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.namPermadrState"
								size="35" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.codPermadrState" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblCity" key="label.skht.customer.city" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.namPermadrCity"
								size="35" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.codPermadrCity" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblJumlahPembayaran" key="label.skht.jumlah.pembayaran" />
						</td>
						<td>
							<s:textfield 
								name="strData.setoranAwal"
								size="20" 
								maxlength="100"
								cssClass="cls-numeric ui-widget ui-widget-content"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.setoranAwal" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblTerbilang" key="label.skht.terbilang" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.terbilang"
								size="30" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
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
								name="svd.occupationdesc"
								size="20" 
								maxlength="105"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.txtProfessCat" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblMaritalStatus" key="label.skht.customer.marital.status" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.marstatdesc"
								size="15" 
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.codCustMarstat" />
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblEducationName" key="label.skht.education.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="svd.txtCustEducn"
								size="20" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase"
								onblur="javascript:this.value=this.value.toUpperCase();"
								disabled="false"
								readonly="true"
							/>
							<s:hidden name="svd.codCustEducn" />
						</td>
					</tr>
					<tr class="cls-row-button"/>
					<tr class="cls-row-button">
						<td align="right" style="padding-right: 5px">
							<sj:submit
								id="btnPosting"
								buttonIcon="ui-icon-gear"
								button="true"
								targets="ph-main"
								key="button.posting"
								onBeforeTopics="btnPosting_beforeSubmit"
							/>
						</td>
						<td style="padding-left: 5px">
							<sj:submit
								id="btnGeneratePDF"
								buttonIcon="ui-icon-gear"
								button="true"
								targets="ph-main"
								key="button.generate.pdf"
								onBeforeTopics="print_beforeSubmit"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<s:token />
		
		<div id="divSkhtAwalDeposit"></div>
		<div id="divSkhtAwalMessage"></div>
		
		<s:hidden name="strData.idMaintainedSpv" />
		<s:hidden name="strData.cifNo" />
		<s:hidden name="umur" />
		<s:hidden name="svd.currencyCode" />
		<s:hidden name="svd.acctNoInq" />
		<s:hidden name="state" />
	</s:form>
	
	<s:form id="frmPrint" action="" method="">
		<s:hidden name="skDepResp.validationNo" />
		<s:hidden name="skDepResp.jamaahName2" />
		<s:hidden name="skDepResp.NIK2" />
		<s:hidden name="skDepResp.address2" />
		<s:hidden name="skDepResp.departureYear" />
		<s:hidden name="skDepResp.embarkasi" />
		<s:hidden name="skDepResp.kloter" />
		
		<s:hidden name="skpDep.refNo" />
		<s:hidden name="skpDep.hajiType" />
		<s:hidden name="skpDep.trxDate" />
		<s:hidden name="skpDep.jamaahName" />
		<s:hidden name="skpDep.NIK" />
		<s:hidden name="skpDep.birthPlace" />
		<s:hidden name="skpDep.birthDate" />
		<s:hidden name="skpDep.address" />
		<s:hidden name="skpDep.postalCode" />
		<s:hidden name="skpDep.villageName" />
		<s:hidden name="skpDep.districtName" />
		<s:hidden name="skpDep.cityName" />
		<s:hidden name="skpDep.gender" />
		<s:hidden name="skpDep.acctNo" />
		<s:hidden name="skpDep.currencyCode" />
		<s:hidden name="skpDep.initialDeposit" cssClass="cls-money" />
		<s:hidden name="skpDep.acctVA" />
		<s:hidden name="skpDep.bpsBranchCode" />
		<s:hidden name="skpDep.provinceCode" />
		<s:hidden name="skpDep.cityCode" />
		<s:hidden name="skpDep.education" />
		<s:hidden name="skpDep.maritalStatus" />
		<s:hidden name="skpDep.fatherName" />
		<s:hidden name="skpDep.responseCode" />
		<s:hidden name="skpDep.validationNo" />
		<s:hidden name="skpDep.provinceName" />
		<s:hidden name="skpDep.occupation" /> 
		<s:hidden name="skpDep.ageInYear" />
		<s:hidden name="skpDep.ageInMonth" />
		<s:hidden name="skpDep.bankName" />
		<s:hidden name="skpDep.branchName" />
		<s:hidden name="skpDep.branchAddress" />
	</s:form>
	
	<%-- Hidden Object Field --%>
	<s:hidden name="sessions" value="%{#session.SKHT}" />
	
	<%-- Buttons --%>
	<sj:a id="btnLookupState" button="true">...</sj:a>
	<sj:a id="btnLookupCity" button="true">...</sj:a>
	<sj:a id="btnLookupMaritalStatus" button="true">...</sj:a>
	<sj:a id="btnLookupEducationName" button="true">...</sj:a>
	<sj:a id="btnLookupOccupation" button="true">...</sj:a>
	<sj:a id="btnLookupHajiType" button="true">...</sj:a>
	
	<div id="divHidded" style="display:none">
		<s:label id="lblNoVirtualAcct" key="label.skht.customer.nova" requiredLabel="true" />
	</div>
	
	
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
					.css({fontSize: 11, lineHeight: 1.7, width: 200, marginLeft: 5})
					.append(
						FPDF('div')
							.css({width: 40})
							.text(isLabel? getLabelText(labelId): " ")
					)
					.append(
						FPDF('div')
							.css({width: 5})
							.text(isLabel? ":": "")
					)
					.append(
						FPDF('div')
						.css({width: 150, fontWeight: 'bold', fontSize: 11})
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
				generatePDFContent().save("SiskohatSetoranAwal.pdf");
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
					generatePDFContent().save("SiskohatSetoranAwal.pdf");
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
				var arrBirth = getDataText("frmMain_birthPlace").split(",");
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
										.append(FPDFHeaderRow("lblNoValidasi", "frmPrint_skpDep_validationNo"))
										.append(FPDFHeaderRow("lblNamaBank", "frmPrint_skpDep_bankName"))
										.append(FPDFHeaderRow("lblKantorCabang", "frmPrint_skpDep_branchName"))
										.append(FPDFHeaderRow("lblAlamatBank", "frmPrint_skpDep_branchAddress"))
								)
						)
						.append(
							FPDF('div')
								.text("TANDA BUKTI SETORAN AWAL")
								.css({
									fontSize: 14,
									lineHeight: 1,
									fontStyle: 'bold',
									//fontFamily: 'sans-serif',
									color: '000000',
									textAlign: 'center'
								})
						)
						.append(
							FPDF('div')
								.text("BIAYA PENYELENGGARAAN IBADAH HAJI")
								.css({
									fontSize: 14,
									lineHeight: 1,
									//fontFamily: 'sans-serif',
									marginBottom: 5,
									fontStyle: 'bold',
									color: '000000',
									textAlign: 'center'
								})
						)
						
						.append(FPDFNewRow("lblNoRekening", "frmPrint_skpDep_acctNo"))
						.append(FPDFNewRow("lblName", "frmPrint_skpDep_jamaahName"))
						.append(FPDFNewRow("lblGender", "frmPrint_skpDep_gender"))
						.append(FPDFNewRow("lblBirthPlace", "frmPrint_skpDep_birthPlace"))
						.append(FPDFNewRow("lblBirthDate", "frmPrint_skpDep_birthDate"))
						.append(FPDFNewRow("lblAddress", "frmPrint_skpDep_address"))
						.append(FPDFNewRow("lblPostalCode", "frmPrint_skpDep_postalCode"))
						.append(FPDFNewRow("lblDesaName", "frmPrint_skpDep_villageName"))
						.append(FPDFNewRow("lblKecamatanName", "frmPrint_skpDep_districtName"))
						.append(FPDFNewRow("lblCity", "frmPrint_skpDep_cityName"))
						.append(FPDFNewRow("lblState", "frmPrint_skpDep_provinceName"))
						.append(FPDFNewRow("lblEducationName", "frmPrint_skpDep_education"))
						.append(FPDFNewRow("lblOccupation", "frmPrint_skpDep_occupation"))
						.append(FPDFNewRow("lblJumlahPembayaran", "frmPrint_skpDep_initialDeposit"))
						.append(FPDFNewRow("lblTerbilang", "frmMain_svd_terbilang"))
						.append(FPDFNewRow("lblNoVirtualAcct", "frmPrint_skpDep_acctVA"))
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
								.text($("#frmPrint_skpDep_cityName").val().replace(/KOTA/i, '').trim() + ',' + ' ' + '<s:date name="%{#session.dtBusiness}" format="dd MMMM yyyy" />')
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
										.text($("#frmPrint_skpDep_jamaahName").val())
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
			var messaging = "Please Wait Your Request Being Processed..";
			var sessionF = '<s:property value="%{#session.SKHT}" />';
			var SKHT_Inqury = '<s:property value="%{#session.SKHT_Inqury}" />';
			
			$wtg = $("div[role='dialog']").find("div[id='divSkhtAwal']");
			$wtg.dialog("close");
			$wtg.dialog("destroy");
			//$wtg.remove();
			
			$dsa = $("div[role='dialog']").find("div[id='divSkhtAwalDeposit']");
			$dsa.dialog("close");
			$dsa.dialog("destroy");
			//$dsa.remove();
			
			$dsb = $("div[role='dialog']").find("div[id='divSkhtAwalMessage']");
			$dsb.dialog("close");
			$dsb.dialog("destroy");
			//$dsb.remove();
			
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frmInquiry, #frmMain").find('.wwlbl').attr('style','width:100%;float:left;text-align:right;vertical-align:middle');
			$("#frmInquiry, #frmMain").find("div[id^='wwgrp_lbl']").parent().width(165);
			$("#frmMain_noValidasi").closest("tr").hide();
			$("#frmMain_namaBank").closest("tr").hide();
			$("#frmMain_kantorCabang").closest("tr").hide();
			$("#frmMain_alamatBank").closest("tr").hide();
			$("#frmMain_kodePIHK").closest("tr").hide();
			$("#frmMain_namaPIHK").closest("tr").hide();
			$("#frmMain_noRegist").closest("tr").hide();
			$("#frmMain_noRekening").closest("tr").hide();
			
			$("#frmMain_umur").val('<s:property value="%{ages}" />');
			$("#frmMain_currencyCode").val('<s:property value="%{currencyCode}" />');
			$("#frmMain_strData_setoranAwal, #frmPrint_skpDep_initialDeposit").autoNumeric("init", {aSign: 'Rp. ', aSep: '.', aDec: ','});
			
			$("#frmMain").find("table > tbody > tr > td:nth-child(2)").css("min-width", "260px");
			$("#frmMain").find("tbody").children().css("height", "26px");
			
			$("#frmMain > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("width", function() {
					var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
					return width_;
				});
			
			filterKeysNumeric();
			
			var sessionManagement = '<s:property value="%{#session.SKHT}" />';
			if (sessionManagement == "POST") {
				console.log("CHANGE BACK");
				$("#tempformReset").click();
				sesParam = {};
				sesParam.onClose = function(sessionFlag) {
					if (sessionFlag != "") {
						console.log("CHANGE : " + '<s:property value="%{#session.SKHT}" />');
					}
				}
			}
			
			$(".ui-autocomplete-input", $("#frmMain")).addClass("ui-widget ui-widget-content");
			$("#frmInquiry_acctNoInq").parent().append('&nbsp;').append($("#btnSearchInq").detach());
			$("#frmInquiry_acctNoInq").parent().append('&nbsp;').append($("#btnSearchInq").detach());
			$("#frmMain_svd_namPermadrState").parent().append('&nbsp;').append($("#btnLookupState").detach());
			$("#frmMain_svd_namPermadrCity").parent().append('&nbsp;').append($("#btnLookupCity").detach());
			$("#frmMain_svd_marstatdesc").parent().append('&nbsp;').append($("#btnLookupMaritalStatus").detach());
			$("#frmMain_svd_occupationdesc").parent().append('&nbsp;').append($("#btnLookupOccupation").detach());
			$("#frmMain_svd_txtCustEducn").parent().append('&nbsp;').append($("#btnLookupEducationName").detach());
			$("#frmMain_objData_hajiId").parent().append('&nbsp;').append($("#btnLookupHajiType").detach());
			
			function myDialog(eButton, title, eDialog, eId, eDesc, closeFunction, beforeOpenFunction) {
				return function() {
					if (!(
							(eButton.button('option').disabled != undefined) &&
								(eButton.button('option', 'disabled'))
						))
					{
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
							.bind("dialogopen", function() {
								if (beforeOpenFunction != undefined)
									beforeOpenFunction();
								
								$("#" + eDialog).click();
							})
							.unbind("dialogclose")
							.bind("dialogclose", function() {
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
			
			function disableElementId(elemId, isDisable) {
				$("#" + elemId).attr("disabled", (isDisable == true)? "true": null);
			}
			
			function getAcctNo() {
				var s = $.trim($("#frmMain_sessions").val());
				$('#frmAcctNo_acctNoInq').attr('value', $('#frmInquiry_acctNoInq').val());
				$('#frmMain_svd_acctNoInq').attr('value', $('#frmInquiry_acctNoInq').val());
				
				$('#aAcctNoInquiry').click();
			}
			
			function checkMappingWhetherNotFound(idForm, idElement, idPickList) {
				var $form = $("#" + idForm);
				var $elem = $("#" + idElement);
				
				if ($elem.val().trim() == '')
					requiredStringValidation($form.get(0), $elem.prop("name"), "Mapping not found", true);
				else if (idPickList != undefined)
					$("#" + idPickList).button("disable");
			}
			
			function clearErrorLabel(idElement, idLabel) {
				$("div[errorfor='" + idElement + "']").remove();
				$("label[for='" + idLabel + "']")
					.removeClass("errorLabel")
					.addClass("label")
					.removeAttr("classname");
			}
			
			//tittle 31301
			if ($("#frmMain_state").val() == 0) {
				// //(($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				rdb.callCurrent();
				var messageType = '<s:property value="%{#request.SKHT_MESSAGE_TYPE}" />';
				var state = parseInt($("#frmMain_state").val());
				
				<s:if test="%{#request.SKHT_MESSAGE.equals('') == false}">
					messageBoxClass(parseInt(messageType), "divSkhtAwalMessage", '<s:property value="%{#request.SKHT_MESSAGE}" />');
				</s:if>
				
				if (state > 0) {
					if (rdb.current == 'add') {
						$("#frmInquiry_acctNoInq").attr("readonly", true);
						$("#btnSearchInq").button("disable");
						
						// disable picklist if any mapping data
						var listCheck = "frmMain_svd_namPermadrState|btnLookupState;";
						listCheck += "frmMain_svd_namPermadrCity|btnLookupCity;";
						listCheck += "frmMain_svd_occupationdesc|btnLookupOccupation;";
						listCheck += "frmMain_svd_txtCustEducn|btnLookupEducationName;";
						listCheck += "frmMain_svd_marstatdesc|btnLookupMaritalStatus;";
						
						var arrCheck = listCheck.substring(0, listCheck.length - 1).split(";");
						
						if ((state == 1) && messageType == '') {
							// disable picklist if any mapping data
							for (var a=0; a<arrCheck.length; a++) {
								var data = arrCheck[a].split("|");
								checkMappingWhetherNotFound("frmMain", data[0], data[1]);
							}
							
							$("#btnPosting").button("enable");
						}
						else if (state == 2) {
							for (var a=0; a<arrCheck.length; a++) {
								var data = arrCheck[a].split("|");
								checkMappingWhetherNotFound("frmMain", data[0], data[1]);
							}
							
							$("#btnLookupHajiType").button("disable");
							$("#btnGeneratePDF").button("enable");
						}
						
					}
				}
			}
			
			
			$("#btnLookupState").click(myDialog(
				$(this), '<s:text name="label.skht.customer.state" />', 'aDlgState',
				'frmMain_svd_codPermadrState', 'frmMain_svd_namPermadrState', 
				function() { clearErrorLabel("frmMain_svd_namPermadrState", "lblState"); }
			));
			
			$("#btnLookupCity").click(myDialog(
				$(this), '<s:text name="label.skht.customer.city" />', 'aDlgCity',
				'frmMain_svd_codPermadrCity', 'frmMain_svd_namPermadrCity',
				function() { clearErrorLabel("frmMain_svd_namPermadrCity", "lblCity"); },
				function() { $("#frmDlgCity_term").val($("#frmMain_svd_codPermadrState").val()); }
			));
			
			$("#btnLookupMaritalStatus").click(myDialog(
				$(this), '<s:text name="label.skht.customer.marital.status" />', 'aDlgMaritalStatus',
				'frmMain_svd_codCustMarstat', 'frmMain_svd_marstatdesc'
			));
			
			$("#btnLookupEducationName").click(myDialog(
				$(this), '<s:text name="label.skht.education.name" />', 'aDlgEducationName',
				'frmMain_svd_codCustEducn','frmMain_svd_txtCustEducn',
				function() { clearErrorLabel("frmMain_svd_txtCustEducn", "lblEducationName"); }
			));
			
			$("#btnLookupOccupation").click(myDialog(
				$(this), '<s:text name="label.skht.customer.occupation" />', 'aDlgOccupation',
				'frmMain_svd_txtProfessCat', 'frmMain_svd_occupationdesc',
				function() { clearErrorLabel("frmMain_svd_occupationdesc", "lblOccupation"); }
			));
			
			$("#btnLookupHajiType").click(myDialog(
				$(this), '<s:text name="label.skht.customer.haji.type" />', 'aDlgHajiType',
				'frmMain_hajiType', 'frmMain_objData_hajiId'
			));
			
			
			$("#btnSearchInq").subscribe("beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				var noAct = $.trim($('#frmInquiry_acctNoInq').val());
				if (noAct == '') {
					messageBoxClass(1, "divSkhtAwalMess",'Please input the Account Number', function(){ $("#ph-main").scrollTop(0); });
					$('#frmInquiry_acctNoInq').focus();
				}
				else if (noAct.length != 12) {
					messageBoxClass(1, "divSkhtAwalMess",'Input Number is not valid', function(){ $("#ph-main").scrollTop(0); });
					$('#frmInquiry_acctNoInq').focus();
				}
				else {
					waitingMessage(3, messaging, "divSkhtAwal");
					getAcctNo();
				}
			});
			
			$("#btnGeneratePDF")
				.unsubscribe("print_beforeSubmit")
				.subscribe("print_beforeSubmit", function(event) {
					event.originalEvent.options.submit = false;
					var sessionFlag = '<s:property value="%{#session.SKHT}" />';
					
					if (($("#frmMain_state").val() == '2') && (sessionFlag == "CREATED")) {
						$('#frmRequestPrint_reqPrint_acctNo').attr('value', $('#frmMain_svd_acctNoInq').val());
						$('#frmRequestPrint_reqPrint_validationNo').attr('value', $('#frmPrint_skDepResp_validationNo').val());
						$('#frmRequestPrint_reqPrint_hajiType').attr('value', $('#frmMain_hajiType').val());
						$('#frmRequestPrint_strData_cifNo').attr('value', $('#frmMain_strData_cifNo').val());
						waitingMessage(3, messaging, "divSkhtAwal");
						$("#aRequestPrint").click();
						
						tempParams = {};
						tempParams.onClose = function(skpDep, sessionFlag, state) {
							$("div[role='dialog']").find("div[id='divSkhtAwal']")
								.dialog("close")
								.dialog("destroy");
							if ((skpDep != "") && (state == "3")) {
								for(var propt in skpDep)
									if ($("#frmPrint_skpDep_" + propt).hasClass("cls-money"))
										$("#frmPrint_skpDep_" + propt).autoNumeric("set", skpDep[propt]);
									else
										$("#frmPrint_skpDep_" + propt).val(skpDep[propt]);
								
								generatePDF();
							}
							
							$("#btnGeneratePDF").button("disable");
						};
					}
				});
			
			$("#btnPosting")
				.unsubscribe("btnPosting_beforeSubmit")
				.subscribe("btnPosting_beforeSubmit", function(event) {
					event.originalEvent.options.submit = false;
					
					if (validateForm_frmMainAwal()) {
						dlgParams = {};
						dlgParams.idMaintainedSpv = "frmMain_strData_idMaintainedSpv";
						dlgParams.event = event;
						dlgParams.onSubmit = function() {
							waitingMessage(3, messaging, "divSkhtAwalDeposit");
							$('#frmBalance_setoranAwal').attr('value', $('#frmMain_svd_setoranAwal').val());
							$("#aAcctBalanceInquiry").click(); 
							
							tempParam = {};
							tempParam.onClose = function(balance, sessionFlag, svd) {
								if (balance != "") {
									if (balance === "VALIDATE") {
										for(var propt2 in svd){
											console.log("FIELD 2 :" + propt2 + " " + svd[propt2]);
											$("#frmMain_svd_" + propt2).val(svd[propt2]);
										}
										$("#frmMain_strData_setoranAwal").val($("#frmMain_strData_setoranAwal").autoNumeric('get'));
										$("#btnPosting").unsubscribe("btnPosting_beforeSubmit");
										$("#btnPosting").click();
									}
									else {
										messageBoxClass(1, "divSkhtAwalMessage", 'Please Check Balance Before Posting', 
											function(){
												$("#ph-main").scrollTop(0);
												$("#divSkhtAwalDeposit").dialog("close");
											}
										);
									}
								}
								else {
									console.log("JSON failed");
									var idMenu = "31301";
									var control = "balance";
									messageBoxClass(1, "divSkhtAwalMessage", "Failed to retrieve Data, Kick From Screen !!", 
										function(event) {
											$("#frmGo_idMenu").val(idMenu);
											$("#frmGo_goAction").val(control);
											$("#btnGo").click();
										}
									);
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
								if ($("#frmMain__strData_idMaintainedSpv").val() == '') {
									$.get("_js/validation_theme_css_xhtml.js");
								}
							})
							.dialog("open");
					}
					else {
						messageBoxClass(1, "divSkhtAwalMessage", 'Please fill All Mandatory Field', function(){ $("#ph-main").scrollTop(0); });
					}
				});
			});
	</script>
</s:if>
