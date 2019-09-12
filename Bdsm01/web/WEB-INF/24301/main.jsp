<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	
	<s:url var="ajaxUpdateTitle" action="24301_title" />
	<s:url var="ajaxUpdateButtons" action="24301_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="24301" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup WIC Customer Type Inquiry --%>
	<s:form id="frmDlgCustomerTypeInq" action="dlg">
		<s:hidden name="dialog" value="dlgWICCustomerTypeInq" />
		<s:hidden name="master" value="WICCustomer" />
		<s:hidden name="constant" value="true" />
		<s:token />
	</s:form>
	<sj:a id="aDlgCustomerTypeInq" formIds="frmDlgCustomerTypeInq" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Customer ID Type Inquiry --%>
	<s:form id="frmDlgIDTypeInq" action="dlg">
		<s:hidden name="dialog" value="dlgIDType" />
		<s:hidden name="master" value="idType" />
		<s:token />
	</s:form>
	<sj:a id="aDlgIDTypeInq" formIds="frmDlgIDTypeInq" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup WIC Customer Type --%>
	<s:form id="frmDlgCustomerType" action="dlg">
		<s:hidden name="dialog" value="dlgWICCustomerType" />
		<s:hidden name="master" value="WICCustomer" />
		<s:hidden name="constant" value="true" />
		<s:token />
	</s:form>
	<sj:a id="aDlgCustomerType" formIds="frmDlgCustomerType" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Customer ID Type --%>
	<s:form id="frmDlgIDType" action="dlg">
		<s:hidden name="dialog" value="dlgIDType" />
		<s:hidden name="master" value="idType" />
		<s:token />
	</s:form>
	<sj:a id="aDlgIDType" formIds="frmDlgIDType" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup to Dukcapil --%>
	<s:form id="frmLookupDukcapil" action="30501_input">
		<s:token />
	</s:form>
	<sj:a id="aLookupDukcapil" formIds="frmLookupDukcapil" targets="divDukcapil" cssClass="ui-helper-hidden" 
		  onSuccessTopics="dukcapilFormLoaded" />
		  
	<%-- Lookup Gender Type --%>
	<s:form id="frmDlgGender" action="dlg">
		<s:hidden name="dialog" value="dlgGender" />
		<s:hidden name="master" value="gender" />
		<s:hidden name="constant" value="true" />
		<s:token />
	</s:form>
	<sj:a id="aDlgGender" formIds="frmDlgGender" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Marital Status Type --%>
	<s:form id="frmDlgMaritalStatus" action="dlg">
		<s:hidden name="dialog" value="dlgMaritalStatus" />
		<s:hidden name="master" value="maritalStatus" />
		<s:hidden name="constant" value="true" />
		<s:token />
	</s:form>
	<sj:a id="aDlgMaritalStatus" formIds="frmDlgMaritalStatus" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Business Type --%>
	<s:form id="frmDlgBusinessType" action="dlg">
		<s:hidden name="dialog" value="dlgWICBusinessType" />
		<s:hidden name="master" value="bidangUsaha" />
		<s:hidden name="term" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDlgBusinessType" formIds="frmDlgBusinessType" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Source of Funds --%>
	<s:form id="frmDlgSourceOfFunds" action="dlg">
		<s:hidden name="dialog" value="dlgWICSourceOfFunds" />
		<s:hidden name="master" value="sourceOfFunds" />
		<s:hidden name="constant" value="true" />
		<s:token />
	</s:form>
	<sj:a id="aDlgSourceOfFunds" formIds="frmDlgSourceOfFunds" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Income per Month Type --%>
	<s:form id="frmDlgIncomePerMonthType" action="dlg">
		<s:hidden name="dialog" value="dlgIncomePerMonthType" />
		<s:hidden name="master" value="incomePerMonthType" />
		<s:token />
	</s:form>
	<sj:a id="aDlgIncomePerMonthType" formIds="frmDlgIncomePerMonthType" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<s:form id="frmAutocompleter">
		<s:hidden name="page" value="1" />
		<s:hidden name="rows" value="10" />
	</s:form>
	
	<script type="text/javascript">
		<%@include file="formValidation.js" %>
	</script>
	
	<s:form id="frmInquiry" name="frmInquiry" action="24301_execute" theme="css_xhtml">
		<fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblCustomerTypeInq" key="label.wic.id.customer.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.customerTypeInqDesc"
								size="30"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="strData.customerTypeInq" />
						</td>
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
					</tr>
					
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
						<td colspan="2" align="right">
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
			
			<s:token />
			<hr class="ui-widget-content" />
		</fieldset>
	</s:form>
	
	<s:form id="frmMain" name="frmMain" action="24301" theme="css_xhtml">
		<fieldset id="fsIdentification" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.identification" /></legend>
			<table>
				<tbody>
					<tr id="rowWICNo">
						<td>
							<s:label id="lblWICNo" key="label.wic.id.wic.no" />
						</td>
						<td>
							<s:label id="WICNo" />
							<s:hidden name="WICNo" />
						</td>
						<td colspan="2" />
					</tr>
					
					<tr id="rowCustomerType">
						<td>
							<s:label id="lblCustomerType" key="label.wic.id.customer.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.customerType"
								size="30"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
								disabled="true"
							/>
							<s:hidden name="customerType" />
						</td>
						<td>
							<s:label id="lblIdNumber" key="label.wic.id.id.number" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="idNumber"
								size="37"
								maxlength="40"
								cssClass="ui-widget ui-widget-content"
								cssStyle="text-transform:uppercase" 
								onblur="this.value=this.value.toUpperCase();"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblIdType" key="label.wic.id.id.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.idType"
								size="30"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
								disabled="true"
							/>
							<s:hidden name="idType" />
						</td>
						<td>
							<s:label id="lblIdExpiredDate" key="label.wic.id.id.expired" requiredLabel="true" />
						</td>
						<td>
							<sj:datepicker
								id="frmMain_idExpiredDate" 
								name="idExpiredDate"
								displayFormat="dd/mm/yy"
								firstDay="1"
								changeMonth="true"
								changeYear="true"
								yearRange="-5:2099"
								onCompleteTopics="onDPClose"
								buttonImageOnly="true"
								showOn="both"
								readonly="true"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsIdentificationData" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.identification.data" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblName" key="label.wic.customer.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="name"
								size="37"
								maxlength="762" 
								cssClass="cls-alphabet-spc ui-widget ui-widget-content" 
							/>
						</td>
						<td>
							<s:label id="lblGender" key="label.wic.customer.gender" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.gender"
								size="30" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="gender" />
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblAddress" key="label.wic.customer.address" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="address1"
								size="37"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblMaritalStatus" key="label.wic.customer.marital.status" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.maritalStatus"
								size="30" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="maritalStatus" />
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="address2"
								size="37"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblBirthPlace" key="label.wic.customer.birth.place" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="birthPlace"
								size="37" 
								maxlength="40"
								cssClass="cls-alphabet-spc ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="address3"
								size="37" 
								maxlength="105"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblBirthDate" key="label.wic.customer.birth.date" requiredLabel="true" />
						</td>
						<td>
							<sj:datepicker
								id="frmMain_birthDate"
								name="birthDate"
								displayFormat="dd/mm/yy"
								firstDay="1"
								changeMonth="true"
								changeYear="true"
								yearRange="-100:-10"
								buttonImageOnly="true"
								showOn="both"
								readonly="true"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblPostalCode" key="label.wic.customer.postalcode" />
						</td>
						<td>
							<s:textfield 
								name="postalCode"
								size="37" 
								maxlength="5"
								cssClass="cls-numeric ui-widget ui-widget-content" 
							/>
						</td>
						<td rowspan="6">
							<s:label id="lblNotes" key="label.wic.customer.notes" requiredLabel="false" />
						</td>
						<td rowspan="6">
							<s:textarea 
								name="notes"
								cssClass="ui-widget ui-widget-content"
								requiredLabel="true"
								rows="6"
								cols="40"
								cssStyle="resize: none"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblCity" key="label.wic.customer.city" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlCityLookup" action="DataMaster_cityList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="city"
								name="city"
								href="%{urlCityLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#city').val() == '') {$('#city_widget').attr('value', '');}"
								onchange="$('#city').val('');"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblState" key="label.wic.customer.state" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlStateLookup" action="DataMaster_stateList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="state"
								name="state"
								href="%{urlStateLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#state').val() == '') {$('#state_widget').attr('value', '');}"
								onchange="$('#state').val('');"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblCountry" key="label.wic.customer.country" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlCountryLookup" action="DataMaster_countryList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="country"
								name="country"
								href="%{urlCountryLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#country').val() == '') {$('#country_widget').attr('value', '');}"
								onchange="$('#country').val('');"
								onSelectTopics="highRiskCheck"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblCitizenship" key="label.wic.customer.citizenship" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlCitizenshipLookup" action="DataMaster_citizenshipList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="citizenship"
								name="citizenship"
								href="%{urlCitizenshipLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#citizenship').val() == '') {$('#citizenship_widget').attr('value', '');}"
								onchange="$('#citizenship').val('');"
								onSelectTopics="highRiskCheck"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblOccupation" key="label.wic.customer.occupation" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlOccupationLookup" action="DataMaster_occupationList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="occupation"
								name="occupation"
								href="%{urlOccupationLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#occupation').val() == '') {$('#occupation_widget').attr('value', '');}"
								onchange="$('#occupation').val('');"
								onSelectTopics="highRiskCheck"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsAdditionalDataPersonal" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.additional.data.personal" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblResidentialAddress" key="label.wic.customer.residential.address" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="residentialAddress1"
								size="37"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td colspan="2" align="center">
							<input type="checkbox" id="chkResSameAsId" name="residentialEqualIdentity" value="true" />
							<s:text name="label.wic.customer.residential.address.sameas.identity" />
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="residentialAddress2"
								size="37"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblBusinessType" key="label.wic.customer.business.type" requiredLabel="true" />
						</td>
						<td style="min-width:320px">
							<s:textfield 
								name="strData.businessType"
								size="42" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="businessType" />
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="residentialAddress3"
								size="37"
								maxlength="105" 
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblSourceOfFunds" key="label.wic.customer.sourceoffunds" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.sourceOfFunds"
								size="30" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:hidden name="sourceOfFunds" />
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblResidentialPostalCode" key="label.wic.customer.postalcode" />
						</td>
						<td>
							<s:textfield 
								name="residentialPostalCode"
								size="37" 
								maxlength="5"
								cssClass="cls-numeric ui-widget ui-widget-content" 
							/>
						</td>
						<td>
							<s:label />
						</td>
						<td>
							<s:label id="lblSourceOfFundsOthers" key="label.wic.customer.sourceoffunds.others" requiredLabel="true" />
							<s:textfield 
								name="sourceOfFundsOthers"
								size="30"
								maxlength="50" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblResidentialCity" key="label.wic.customer.city" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlCityLookup" action="DataMaster_cityList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="residentialCity"
								name="residentialCity"
								href="%{urlCityLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#residentialCity').val() == '') {$('#residentialCity_widget').attr('value', '');}"
								onchange="$('#residentialCity').val('');"
							/>
						</td>
						<td>
							<s:label id="lblTrxPurpose" key="label.wic.customer.trx.purpose" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="trxPurpose"
								size="37"
								maxlength="35" 
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblResidentialState" key="label.wic.customer.state" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlStateLookup" action="DataMaster_stateList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="residentialState"
								name="residentialState"
								href="%{urlStateLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#residentialState').val() == '') {$('#residentialState_widget').attr('value', '');}"
								onchange="$('#residentialState').val('');"
							/>
						</td>
						<td colspan="2" />
					</tr>
					
					<tr>
						<td>
							<s:label id="lblResidentialCountry" key="label.wic.customer.country" requiredLabel="true" />
						</td>
						<td>
							<s:url var="urlCountryLookup" action="DataMaster_countryList" namespace="json" escapeAmp="false" />
							<sj:autocompleter
								id="residentialCountry"
								name="residentialCountry"
								href="%{urlCountryLookup}"
								list="gridTemplate"
								listKey="id"
								listValue="desc"
								delay="50"
								loadMinimumCount="2"
								formIds="frmAutocompleter"
								size="37"
								onblur="if($('#residentialCountry').val() == '') {$('#residentialCountry_widget').attr('value', '');}"
								onchange="$('#residentialCountry').val('');"
							/>
						</td>
						<td colspan="2" />
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsAdditionalDataPersonal2" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.additional.data.personal.2" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblJobTitle" key="label.wic.customer.job.title" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="jobTitle"
								size="37" 
								maxlength="120"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblHomePhoneNo" key="label.wic.customer.home.phone.no" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="homePhoneNo"
								size="37" 
								maxlength="45"
								cssClass="cls-phone ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblOfficeName" key="label.wic.customer.office.name" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="officeName"
								size="37" 
								maxlength="50"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblOfficePhoneNo" key="label.wic.customer.office.phone.no" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="officePhoneNo"
								size="37" 
								maxlength="45"
								cssClass="cls-phone ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblOfficeAddress" key="label.wic.customer.office.address" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="officeAddress1"
								size="37" 
								maxlength="105"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblMobilePhoneNo" key="label.wic.customer.mobile.phone.no" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="mobilePhoneNo"
								size="37" 
								maxlength="45"
								cssClass="cls-phone ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="officeAddress2"
								size="37" 
								maxlength="105"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td>
							<s:label id="lblIncomePerMonthType" key="label.wic.customer.income.type" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="strData.incomePerMonthType"
								size="30"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
								readonly="true"
							/>
						</td>
						<s:hidden name="incomePerMonthType" />
					</tr>

					<tr>
						<td>
							<s:label />
						</td>
						<td>
							<s:textfield 
								name="officeAddress3"
								size="37" 
								maxlength="105"
								cssClass="ui-widget ui-widget-content" 
								requiredLabel="true"
							/>
						</td>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsHighRiskInformation" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.highrisk.information" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblHighRiskWIC" key="label.wic.wic.highrisk.wic" />
						</td>
						<td>
							<s:textfield 
								name="highRiskWIC"
								size="37"
								maxlength="50"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
						<td>
							<s:label id="lblHighRiskCountry" key="label.wic.wic.highrisk.country" />
						</td>
						<td>
							<s:textfield 
								name="highRiskCountry"
								size="37"
								maxlength="50"
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
							<s:label id="hdHighRiskCountry" cssStyle="display: none" />
							<s:label id="hdHighRiskCitizenship" cssStyle="display: none" />
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsCourierInformation" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all">Courier Information</legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblInstanceRepresented" key="label.wic.courier.instance.represented" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="instanceRepresented"
								size="37" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
						<td>
							<s:label id="lblAuthLetterNo" key="label.wic.courier.auth.letter.no" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="authLetterNo"
								size="37" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblAccountRepresented" key="label.wic.courier.account.represented" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="accountRepresented"
								size="37" 
								maxlength="100"
								cssClass="cls-numeric ui-widget ui-widget-content" 
							/>
						</td>
						<td>
							<s:label id="lblAccountBranch" key="label.wic.courier.account.branch" requiredLabel="true" />
						</td>
						<td>
							<s:textfield 
								name="accountBranch"
								size="37" 
								maxlength="100"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<fieldset id="fsSystemInformation" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.wic.fieldset.legend.system.information" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblCreateBranch" key="label.wic.system.create.branch" />
						</td>
						<td>
							<s:textfield 
								name="branch"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
						<td>
							<s:label id="lblCreateDateTime" key="label.wic.system.create.datetime" />
						</td>
						<td>
							<s:textfield 
								name="dtmCreated"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblUserRequester" key="label.wic.system.user.requester" />
						</td>
						<td>
							<s:textfield 
								name="requester"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true" 
							/>
						</td>
						<td>
							<s:label id="lblLastMaintenance" key="label.wic.system.last.maintenance" />
						</td>
						<td>
							<s:textfield 
								name="dtmUpdated"
								size="37" 
								cssClass="ui-widget ui-widget-content"
								readonly="true"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:label id="lblUserApprover" key="label.wic.system.user.approver" />
						</td>
						<td>
							<s:textfield 
								name="approver"
								size="37" 
								cssClass="ui-widget ui-widget-content" 
								readonly="true"
							/>
						</td>
						<td colspan="2">
							&nbsp;
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
							 	disabled="true"
							 	targets="ph-main"
							 	key="button.save"
							 	onBeforeTopics="btnProcess_beforeSubmit"
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		
		<s:hidden name="flagStatus" />
		<s:token />
	</s:form>
	
	<div id="divMessage" title=""></div>
	<div id="divDukcapil"></div>
	
	
	<%-- Buttons --%>
	<sj:a id="btnLookupCustomerTypeInq" button="true">...</sj:a>
	<sj:a id="btnLookupIdTypeInq" button="true">...</sj:a>
	
	<sj:a id="btnLookupCustomerType" button="true">...</sj:a>
	<sj:a id="btnLookupIdType" button="true">...</sj:a>
	<sj:a id="btnLookupDukcapil" button="true" cssStyle="visibility:hidden">Dukcapil</sj:a>
	
	<sj:a id="btnLookupGender" button="true">...</sj:a>
	<sj:a id="btnLookupMaritalStatus" button="true">...</sj:a>
	<sj:a id="btnLookupBusinessType" button="true" onClickTopics="btnLookupBusinessTypeBefore">...</sj:a>
	<sj:a id="btnLookupSourceOfFunds" button="true">...</sj:a>
	<sj:a id="btnLookupIncomePerMonthType" button="true">...</sj:a>
	
	
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			/* === [BEGIN] alter display === */
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frmInquiry, #frmMain").find('.wwlbl').attr('style','width:100%;float:left;text-align:right;vertical-align:middle');
			$("#frmInquiry, #frmMain").find("div[id^='wwgrp_lbl']").parent().width(165);
				
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
			
			
			$("#frmInquiry_strData_customerTypeInqDesc").parent().append('&nbsp;').append($("#btnLookupCustomerTypeInq").detach());
			$("#frmInquiry_strData_idTypeInqDesc").parent().append('&nbsp;').append($("#btnLookupIdTypeInq").detach());
			
			$("#frmMain_strData_customerType").parent().append('&nbsp;').append($("#btnLookupCustomerType").detach());
			$("#frmMain_strData_idType").parent().append('&nbsp;').append($("#btnLookupIdType").detach());
			$("#frmMain_idNumber").parent().append('&nbsp;').append($("#btnLookupDukcapil").detach());
			$("#frmMain_strData_gender").parent().append('&nbsp;').append($("#btnLookupGender").detach());
			$("#frmMain_strData_maritalStatus").parent().append('&nbsp;').append($("#btnLookupMaritalStatus").detach());
			$("#frmMain_strData_businessType").parent().append('&nbsp;').append($("#btnLookupBusinessType").detach());
			$("#frmMain_strData_sourceOfFunds").parent().append('&nbsp;').append($("#btnLookupSourceOfFunds").detach());
			$("#frmMain_strData_incomePerMonthType").parent().append('&nbsp;').append($("#btnLookupIncomePerMonthType").detach());
			
			$("#wwlbl_lblSourceOfFundsOthers").append('&nbsp;').append($("#frmMain_sourceOfFundsOthers").detach());
			$("#wwlbl_lblSourceOfFundsOthers").css("text-align", "left");
			$("#wwlbl_lblSourceOfFundsOthers").hide();
			
			
			$("#frmMain img.ui-datepicker-trigger")
				.css("margin-left", "3px")
				.addClass("ui-helper-hidden");
			$("#frmMain_notes").closest("td").attr('style', 'vertical-align: top');
			$("#frmMain_notes")
				.attr("maximumLength", "100")
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
				if ($("#actionError").length == 0)
					$("#rbBS").data("rdb").clear(false);
			}
			
			var highRiskCheck = function(event, ui) {
				var masterType = ui.id.split("_")[0];
				
				if (event.handled !== true) {
					$.post(
						"json/DataMaster_get.action", 
						{master: masterType, id: event.originalEvent.ui.item.key}, 
						function(data) {
							var highRisk = data.data.data1;
							if ((highRisk != null) && (highRisk != '')) {
								if (masterType == 'country' || masterType == 'citizenship') {
									messageBox(1, '<s:text name="24301.warning.highrisk.citizenship_country" />', 
										setHighRisk_Country_Citizenship(masterType, highRisk));
								}
								else if (masterType == 'occupation') {
									messageBox(1, '<s:text name="24301.warning.highrisk.occupation" />');
									$("#frmMain_highRiskWIC").attr("value", highRisk);
								}
							}
							else {
								if (masterType == 'country' || masterType == 'citizenship')
									setHighRisk_Country_Citizenship(masterType, "");
								else if (masterType == 'occupation')
									$("#frmMain_highRiskWIC").attr("value", "");
							}
						}, 
						"json"
					);
					event.handled = true; // prevent repeating post ajax request
				}
			};
			
			function setHighRisk_Country_Citizenship(source, value) {
				if (source == 'country')
					$("#hdHighRiskCountry").attr("value", value);
				else if (source == 'citizenship')
					$("#hdHighRiskCitizenship").attr("value", value);
				
				strTemp = $("#hdHighRiskCountry").attr("value") + "|" + $("#hdHighRiskCitizenship").attr("value");
				if (strTemp == '|')
					strTemp = '';
				
				$("#frmMain_highRiskCountry").attr("value", strTemp);
			}
			
			$("#country_widget, #citizenship_widget, #occupation_widget")
				.unsubscribe("highRiskCheck")
				.subscribe("highRiskCheck", highRiskCheck);
			
			
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
			
			function lookupDataMaster(master, id, constant, fieldIdName, fieldDescName, noDataFunction, successFunction) {
				if (id != null)
					$.post(
						"json/DataMaster_get.action",
						{
							master: master, 
							id: id, 
							constant: (constant==null)? "false": constant, 
							rows:10, 
							page:1
						},
						function(data) {
							if (data.data.id != null) {
								$(fieldIdName).val(data.data.id);
								$(fieldDescName).val(data.data.desc);
								
								if (successFunction != undefined)
									successFunction();
							}
							else if (noDataFunction != undefined)
								noDataFunction();
						},
						"json"
					);
			}
			
			function removeElementOfDukcapil(name) {
				var $legendResult = $("#divDukcapil").find("legend:nth(1)").parent();
				var $tr = $legendResult.find("#" + name).closest("tr");
				var i = $tr.children().index($("#" + name).parent());
				
				for (var j=i; j>=i-2; j--)
					$tr.children().eq(j).remove();
			}
			
			
			$("#btnLookupCustomerTypeInq").click(myDialog(
				$(this), '<s:text name="label.wic.id.customer.type" />', 'aDlgCustomerTypeInq', 
				'frmInquiry_strData_customerTypeInq', 'frmInquiry_strData_customerTypeInqDesc',
				function() {
					var custType = $("#frmInquiry_strData_customerTypeInq").val();
					if ( custType == '1') { /* WIC */
						$("#fsAdditionalDataPersonal2").show();
						$("#fsHighRiskInformation").show();
						$("#fsCourierInformation").hide();
					}
					else if (custType == '2') { /* Courier */
						$("#fsAdditionalDataPersonal2").hide();
						$("#fsHighRiskInformation").hide();
						$("#fsCourierInformation").show();
					}
				}
			));
			
			$("#btnLookupIdTypeInq").click(myDialog(
				$(this), '<s:text name="label.wic.id.id.type" />', 'aDlgIDTypeInq', 
				'frmInquiry_strData_idTypeInq', 'frmInquiry_strData_idTypeInqDesc'
			));
			
			$("#btnLookupCustomerType").click(myDialog(
				$(this), '<s:text name="label.wic.id.customer.type" />', 'aDlgCustomerType', 
				'frmMain_customerType', 'frmMain_strData_customerType',
				function() {
					var custType = $("#frmMain_customerType").val(); 
					if (custType == '1') { /* WIC */
						$("#fsAdditionalDataPersonal2").show();
						$("#fsHighRiskInformation").show();
						$("#fsCourierInformation").hide();
					}
					else if (custType == '2') { /* Courier */
						$("#fsAdditionalDataPersonal2").hide();
						$("#fsHighRiskInformation").hide();
						$("#fsCourierInformation").show();
					}
				}
			));
			
			$("#btnLookupIdType").click(myDialog(
				$(this), '<s:text name="label.wic.id.id.type" />', 'aDlgIDType', 
				'frmMain_idType', 'frmMain_strData_idType',
				function() {
					$("#btnLookupDukcapil").css("visibility", 
						($("#frmMain_idType").val() == "KTP") && ((rdb.current == 'add') || (rdb.current == 'edit'))? 
							"visible" : "hidden");
				}
			));
			
			/* Button Dukcapil - BEGIN */
			$("#btnLookupDukcapil").click(function() {
				$("#aLookupDukcapil").click();
			});
			$("#aLookupDukcapil")
				.unsubscribe("dukcapilFormLoaded")
				.subscribe("dukcapilFormLoaded", function(event) {
					var $divDukcapil = $("#divDukcapil");
					var $legendResult = $divDukcapil.find("legend:nth(1)").parent(); // Fieldset Result
					
					//$divDukcapil.find("legend:first").parent().hide(); // Hide fieldset Input
					/* Add buttons : validate and cancel  */
					$legendResult
						.append("<br>")
						.append("<input type='button' id='btnValidate' name='button.validate' value='Validate'/>")
						.append("&nbsp;&nbsp;")
						.append("<input type='button' id='btnCancel' name='button.cancel' value='Cancel'/>");
						
					$divDukcapil.find("input[type='button']").addClass("ui-button ui-widget ui-state-default ui-corner-all");
					
					if (navigator.appName.indexOf("Internet Explorer") > -1) {
						$divDukcapil.find("*[role='button']").css("position", "static");
						$divDukcapil.find("input[type='button']").css("position", "static");
					}
					
					/* hide field blood type, father name, mother name and KK */
					removeElementOfDukcapil("frmMain_model_bloodType");
					removeElementOfDukcapil("frmMain_model_namFather");
					removeElementOfDukcapil("frmMain_model_namMother");
					removeElementOfDukcapil("frmMain_model_nokk");
					
					
					$divDukcapil.find("#btnValidate").click(function() {
						if ($("#frmMain_model_name").val() != "") {
							$("#frmMain_name").val($("#frmMain_model_name").val());
							$("#frmMain_birthPlace").val($("#frmMain_model_birthPlace").val());
							$("#frmMain_address1").val($("#frmMain_model_address").val());
							$("#frmMain_address2").val("RT " + $("#frmMain_model_rt").val() + "  RW " + $("#frmMain_model_rw").val());
							$("#frmMain_address3").val($("#frmMain_model_namKel").val() + ", " + $("#frmMain_model_namKec").val());
							$("#frmMain_postalCode").val($("#frmMain_model_posCode").val());
							
							var val, id;
							/* city */
							id = $("#frmMain_model_namKab").val().trim();
							lookupDataMaster("cityDukcapilToNCBS", id, "false", "input[name='city']", "#city_widget", 
								function() { $("#city_widget").val(""); addError($("#city").get(0), "Mapping not found"); },
								function() { $("#city_widget").attr("readonly", "true"); }
							);
								
							/* state */
							id = $("#frmMain_model_namProp").val().trim();
							lookupDataMaster("stateDukcapilToNCBS", id, "false", "input[name='state']", "#state_widget",
								function() { $("#state_widget").val(""); addError($("#state").get(0), "Mapping not found"); },
								function() { $("#state_widget").attr("readonly", "true"); }
							);
							
							/* country & citizenship */
							id = "ID";
							lookupDataMaster("country", id, "false", "input[name='country']", "#country_widget", null,
								function() {
									$("input[name='citizenship']").val($("input[name='country']").val());
									$("#citizenship_widget").val($("#country_widget").val());
									$("#citizenship_widget").attr("readonly", "true");
									$("#country_widget").attr("readonly", "true");
								} 
							);
							
							/* occupation */
							id = $("#frmMain_model_profession").val().trim();
							lookupDataMaster("occupationDukcapilToNCBS", id, "false", "input[name='occupation']", "#occupation_widget",
								function() { $("#occupation_widget").val(""); addError($("#occupation").get(0), "Mapping not found"); },
								function() { $("#occupation_widget").attr("readonly", "true"); }
							);
							
							/* gender */
							val = $("#frmMain_model_sex").val().trim().toUpperCase(); id = null;
							if (val == "LAKI-LAKI")
								id = "M";
							else if (val == "PEREMPUAN")
								id = "F";
							if (id != null) {
								lookupDataMaster("gender", id, "true", "#frmMain_gender", "#frmMain_strData_gender");
								$("#btnLookupGender").button("disable");
							}
							
							/* marital status */
							val = $("#frmMain_model_marStat").val().trim().replace(/[ ]+/g, " ").toUpperCase(); id = null;
							if (val == "BELUM KAWIN")
								id = "1";
							else if (val == "KAWIN")
								id = "2";
							else if (val == "CERAI MATI")
								id = "3";
							else if (val == "CERAI HIDUP")
								id = "4";
							if (id != null) {
								lookupDataMaster("maritalStatus", id, "true", "#frmMain_maritalStatus", "#frmMain_strData_maritalStatus");
								$("#btnLookupMaritalStatus").button("disable");
							}
																
								
							/* date of birth */
							var dob = $("#frmMain_model_dob").val().split("-");
							if (dob[0].length == 4) { // yyyy-MM-dd
								$("#frmMain_birthDate").val(dob[2] + "/" + dob[1] + "/" + dob[0]);
							}
							else { // d-MMM-yyyy
								if (parseInt(dob[0]) < 10)
									dob[0] = "0" + dob[0];
								
								if (dob[1] == "Jan") dob[1] = "01";
								if (dob[1] == "Feb") dob[1] = "02";
								if (dob[1] == "Mar") dob[1] = "03";
								if (dob[1] == "Apr") dob[1] = "04";
								if ((dob[1] == "May") || (dob[1] == "Mei")) dob[1] = "05";
								if (dob[1] == "Jun") dob[1] = "06";
								if (dob[1] == "Jul") dob[1] = "07";
								if (dob[1].substring(0, 1) == "A") dob[1] = "08"; // Aug or Agt
								if (dob[1] == "Sep") dob[1] = "09";
								if (dob[1].substring(0, 1) == "O") dob[1] = "10"; // Oct or Okt
								if (dob[1] == "Nov") dob[1] = "11";
								if (dob[1].substring(0, 1) == "D") dob[1] = "12"; // Dec or Des
									
								$("#frmMain_birthDate").val(dob[0] + "/" + dob[1] + "/" + dob[2]);
							}
						}
						
						$("#frmMain").show();
						$divDukcapil.hide().empty();
					});
					
					
					$divDukcapil.find("#btnCancel").click(function() {
						$("#frmMain").show();
						$divDukcapil.hide().empty();						
					});
					
					$divDukcapil.find("#frmMain_inputNik").val($("#frmMain_idNumber").val());
					$("#frmMain").hide();
					$divDukcapil.show();
					$("#btnFind").click();
					
					$divDukcapil.find("#frmMain_inputNik").attr("readonly", "true");
					$divDukcapil.find("#btnFind").button("disable");
				});
			/* Button Dukcapil - END */
			
			
			$("#btnLookupGender").click(myDialog(
				$(this), '<s:text name="label.wic.customer.gender" />', 'aDlgGender', 
				'frmMain_gender', 'frmMain_strData_gender'
			));
			
			$("#btnLookupMaritalStatus").click(myDialog(
				$(this), '<s:text name="label.wic.customer.marital.status" />', 'aDlgMaritalStatus', 
				'frmMain_maritalStatus', 'frmMain_strData_maritalStatus'
			));
			
			
			$("#btnLookupBusinessType").click(myDialog(
				$(this), '<s:text name="label.wic.customer.business.type" />', 'aDlgBusinessType', 
				'frmMain_businessType', 'frmMain_strData_businessType'
			));
			
			$("#btnLookupBusinessType")
				.unsubscribe("btnLookupBusinessTypeBefore")
				.subscribe("btnLookupBusinessTypeBefore", function(event) {
					$("#frmDlgBusinessType_term").val($("#frmMain_strData_businessType").val());
				});
			
			$("#btnLookupSourceOfFunds").click(myDialog(
				$(this), '<s:text name="label.wic.customer.sourceoffunds" />', 'aDlgSourceOfFunds', 
				'frmMain_sourceOfFunds', 'frmMain_strData_sourceOfFunds',
				function() {
					if ($("#frmMain_sourceOfFunds").val() == "OTH")
						$("#wwlbl_lblSourceOfFundsOthers").show();
					else {
						$("#frmMain_sourceOfFundsOthers").attr("value", "");
						$("#wwlbl_lblSourceOfFundsOthers").hide();
					}
				}
			));
			
			$("#btnLookupIncomePerMonthType").click(myDialog(
				$(this), '<s:text name="label.wic.customer.income.type" />', 'aDlgIncomePerMonthType', 
				'frmMain_incomePerMonthType', 'frmMain_strData_incomePerMonthType'
			));
			
			
			$("#chkResSameAsId").change(function() {
				var isChecked = this.checked;
				
				$("#frmMain_residentialAddress1").attr("value", isChecked? $("#frmMain_address1").attr("value"): "");
				$("#frmMain_residentialAddress2").attr("value", isChecked? $("#frmMain_address2").attr("value"): "");
				$("#frmMain_residentialAddress3").attr("value", isChecked? $("#frmMain_address3").attr("value"): "");
				$("#frmMain_residentialPostalCode").attr("value", isChecked? $("#frmMain_postalCode").attr("value"): "");
				$("#residentialCity").attr("value", isChecked? $("#city").attr("value"): "");
				$("#residentialCity_widget").attr("value", isChecked? $("#city_widget").attr("value"): "");
				$("#residentialState").attr("value", isChecked? $("#state").attr("value"): "");
				$("#residentialState_widget").attr("value", isChecked? $("#state_widget").attr("value"): "");
				$("#residentialCountry").attr("value", isChecked? $("#country").attr("value"): "");
				$("#residentialCountry_widget").attr("value", isChecked? $("#country_widget").attr("value"): "");
				
				$("#frmMain_residentialAddress1").attr("readonly", isChecked? true: null);
				$("#frmMain_residentialAddress2").attr("readonly", isChecked? true: null);
				$("#frmMain_residentialAddress3").attr("readonly", isChecked? true: null);
				$("#frmMain_residentialPostalCode").attr("readonly", isChecked? true: null);
				$("#residentialCity_widget").attr("readonly", isChecked? true: null);
				$("#residentialState_widget").attr("readonly", isChecked? true: null);
				$("#residentialCountry_widget").attr("readonly", isChecked? true: null);
				
				$("#fsAdditionalDataPersonal").find("input[type='text']")
					.css("opacity", '')
					.filter("[readonly]").css("opacity", "0.5");
			});
			
			
			$("#btnProcess")
				.unsubscribe("btnProcess_beforeSubmit")
				.subscribe("btnProcess_beforeSubmit", function(event) {
					$("#frmMain").unbind("submit");
					event.originalEvent.options.submit = false;
					
					if (validateForm_frmMain()) {
						dlgParams = {};
						dlgParams.idMaintainedSpv = "frmMain_approver";
						dlgParams.event = event;
						dlgParams.onSubmit = function() {
							// dlgParams.event.originalEvent.options.submit = true;
							$("#frmMain_requester").val('<s:property value="%{#session.idUser}" />');
							$("#btnProcess").unsubscribe("btnProcess_beforeSubmit");
							
							disableMainDescription(true);
							if (rdb.current == 'delete')
								disableWICElements(true);
							
							$("#btnProcess").click();
						};
						
						if (rdb.current != 'add') { /* update or delete */
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
						else /* insert */
							dlgParams.onSubmit();
					}
				});
			
			
			var funcSearchBeforeSubmit = null;
			funcSearchBeforeSubmit = function(event) {
				event.originalEvent.options.submit = false;
				o = {};
				o.restoreEvent = function() {
					$("#btnSearch").subscribe("btnSearch_beforeSubmit", funcSearchBeforeSubmit);
					disableInquiryDescription(false);
				};
				
				if (validateForm_frmInquiry()) {
					// event.originalEvent.options.submit = true;
					disableInquiryDescription(true);
					$("#btnSearch").unsubscribe("btnSearch_beforeSubmit");
					$("#btnSearch").click();
				}
			}; 
				
			$("#btnSearch")
				.unsubscribe("btnSearch_beforeSubmit")
				.subscribe("btnSearch_beforeSubmit", funcSearchBeforeSubmit);
			
				
			$.subscribe("onDPClose", function(event, inst) {
				var txt = event.originalEvent.dateText;
				if ((txt != '') && !isAfterDate(txt, dtBusiness))
					messageBox(1, '<s:text name="24301.id.id.expired" />');
			});
			
			
			$("#fsAdditionalDataPersonal2").hide();
			$("#fsHighRiskInformation").hide();
			$("#fsCourierInformation").hide();
			
			
			function disableInquiryDescription(isDisable) {
				disableElementId("frmInquiry_strData_customerTypeInqDesc", isDisable);
				disableElementId("frmInquiry_strData_idTypeInqDesc", isDisable);
			}
			
			function disableMainDescription(isDisable) {
				disableElementId("frmMain_strData_customerType", isDisable);
				disableElementId("frmMain_strData_idType", isDisable);
				disableElementId("frmMain_strData_gender", isDisable);
				disableElementId("frmMain_strData_maritalStatus", isDisable);
				disableElementId("city_widget", isDisable);
				disableElementId("state_widget", isDisable);
				disableElementId("country_widget", isDisable);
				disableElementId("citizenship_widget", isDisable);
				disableElementId("occupation_widget", isDisable);
				disableElementId("residentialCity_widget", isDisable);
				disableElementId("residentialState_widget", isDisable);
				disableElementId("residentialCountry_widget", isDisable);
				disableElementId("frmMain_strData_businessType", isDisable);
				disableElementId("frmMain_strData_sourceOfFunds", isDisable);
				
				if ($("#chkResSameAsId").get(0).checked) {
					disableElementId("frmMain_residentialAddress1", isDisable);
					disableElementId("frmMain_residentialAddress2", isDisable);
					disableElementId("frmMain_residentialAddress3", isDisable);
					disableElementId("frmMain_residentialPostalCode", isDisable);
					disableElementId("residentialCity", isDisable);
					disableElementId("residentialState", isDisable);
					disableElementId("residentialCountry", isDisable);
				}
				
				disableElementId("frmMain_strData_incomePerMonthType", isDisable);
				disableElementId("frmMain_dtmCreated", isDisable);
				disableElementId("frmMain_dtmUpdated", isDisable);
			}
			
			function disableWICElements(isDisable) {
				disableElementId("frmMain_customerType", isDisable);
				disableElementId("frmMain_idType", isDisable);
				disableElementId("frmMain_idNumber", isDisable);
				disableElementId("frmMain_strData_idExpiredDate", isDisable);
				disableElementId("frmMain_name", isDisable);
				disableElementId("frmMain_gender", isDisable);
				disableElementId("frmMain_maritalStatus", isDisable);
				disableElementId("frmMain_birthPlace", isDisable);
				disableElementId("frmMain_strData_birthDate", isDisable);
				disableElementId("frmMain_address1", isDisable);
				disableElementId("frmMain_address2", isDisable);
				disableElementId("frmMain_address3", isDisable);
				disableElementId("frmMain_postalCode", isDisable);
				disableElementId("frmMain_city", isDisable);
				disableElementId("frmMain_state", isDisable);
				disableElementId("frmMain_country", isDisable);
				disableElementId("frmMain_citizenship", isDisable);
				disableElementId("frmMain_occupation", isDisable);
				disableElementId("frmMain_notes", isDisable);
				disableElementId("frmMain_residentialAddress1", isDisable);
				disableElementId("frmMain_residentialAddress2", isDisable);
				disableElementId("frmMain_residentialAddress3", isDisable);
				disableElementId("frmMain_residentialPostalCode", isDisable);
				disableElementId("frmMain_residentialCity", isDisable);
				disableElementId("frmMain_residentialState", isDisable);
				disableElementId("frmMain_residentialCountry", isDisable);
				disableElementId("frmMain_jobTitle", isDisable);
				disableElementId("frmMain_homePhoneNo", isDisable);
				disableElementId("frmMain_mobilePhoneNo", isDisable);
				disableElementId("frmMain_officeName", isDisable);
				disableElementId("frmMain_officePhoneNo", isDisable);
				disableElementId("frmMain_officeAddress1", isDisable);
				disableElementId("frmMain_officeAddress2", isDisable);
				disableElementId("frmMain_officeAddress3", isDisable);
				disableElementId("frmMain_incomePerMonthType", isDisable);
				disableElementId("frmMain_highRiskWIC", isDisable);
				disableElementId("frmMain_highRiskCountry", isDisable);
				disableElementId("frmMain_instanceRepresented", isDisable);
				disableElementId("frmMain_accountRepresented", isDisable);
				disableElementId("frmMain_authLetterNo", isDisable);
				disableElementId("frmMain_accountBranch", isDisable);
				disableElementId("frmMain_flagStatus", isDisable);
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
			
			filterKeys(".cls-phone", function(e, elemText_, char_) {
				if (elemText_ == '') {
					if (!char_.match(/[\+0-9(]/))
						e.preventDefault();
				}
				else {
					if (!char_.match(/[0-9() ]/))
						e.preventDefault();
				}
			});
			
			filterKeys(".cls-money", function(e, elemText_, char_) {
				if (!char_.match(/[0-9\.\,]/))
					e.preventDefault();
			});
			
			filterKeys(".cls-numeric", function(e, elemText_, char_) {
				if (!char_.match(/[0-9]/))
					e.preventDefault();
			});
			
			filterKeys(".cls-numeric-spc", function(e, elemText_, char_) {
				if (!char_.match(/[0-9] /))
					e.preventDefault();
			});
			
			filterKeys(".cls-alphabet", function(e, elemText_, char_) {
				if (!char_.match(/[a-z]/i))
					e.preventDefault();
			});
			
			filterKeys(".cls-alphabet-spc", function(e, elemText_, char_) {
				if (!char_.match(/[a-z\. ]/i))
					e.preventDefault();
			});
			
		});
		
		var dtBusiness = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />';
	</script>
</s:if>
