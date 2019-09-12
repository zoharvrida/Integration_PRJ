<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<table>
    <tbody>
        <tr><td>
                <sj:checkboxlist id="rbBS" list="{'Clear'}" name="rbBS" />
            </td>
            <td id="spinCon" colspan="5" width="1000px">&nbsp;
                <span id="spinner" class="ui-helper-hidden" style="position:relative; float:right; margin-right:30px;">Loading ...<img src='./spinner1.gif' style='position:absolute; top:0; width:25px; height:25px; z-index:99' /></span>
            </td> 
        </tr>
    </tbody>
</table>
<script type="text/javascript">
   
    function clearResultCASA() {
        $("#frmMain_so_ApplicationID").attr("value", null);
        $("#frmMain_so_CifNumber").attr("value", null);
        $("#frmMain_so_Religion").attr("value", null);
        $("#frmMain_so_CustomerIc").attr("value", null);
        $("#frmMain_so_FullName").attr("value", null);
        $("#frmMain_so_BirthDate").attr("value", null);
        $("#frmMain_so_BranchCode").attr("value", null);
        $("#frmMain_so_AccessCode").attr("value", null);
        $("#frmMain_so_Lob").attr("value", null);
        $("#frmMain_so_Category").attr("value", null);
        $("#frmMain_so_CifType").attr("value", null);
        $("#frmMain_so_ShortName").attr("value", null);
        $("#frmMain_so_Salutation").attr("value", null);
        $("#frmMain_so_CustFirstName").attr("value", null);
        $("#frmMain_so_CustMidName").attr("value", null);
        $("#frmMain_so_CustLastName").attr("value", null);
        $("#frmMain_so_MailAddrs1").attr("value", null);
        $("#frmMain_so_MailAddrs2").attr("value", null);
        $("#frmMain_so_MailAddrs3").attr("value", null);
        $("#frmMain_so_MaillingCode").attr("value", null);
        $("#frmMain_so_MaiilingTownCity").attr("value", null);
        $("#frmMain_so_MaillingState").attr("value", null);
        $("#frmMain_so_MaillingCountry").attr("value", null);
        $("#frmMain_so_PermaAddress1").attr("value", null);
        $("#frmMain_so_PermaAddress2").attr("value", null);
        $("#frmMain_so_PermaAddress3").attr("value", null);
        $("#frmMain_so_PermaZipCode").attr("value", null);
        $("#frmMain_so_PermaTownCity").attr("value", null);
        $("#frmMain_so_PermaState").attr("value", null);
        $("#frmMain_so_PermaCountry").attr("value", null);
        $("#frmMain_so_EmployerAddress1").attr("value", null);
        $("#frmMain_so_EmployerAddress2").attr("value", null);
        $("#frmMain_so_EmployerAddress3").attr("value", null);
        $("#frmMain_so_EmployerZipCode").attr("value", null);
        $("#frmMain_so_EmployerTownCity").attr("value", null);
        $("#frmMain_so_EmployerState").attr("value", null);
        $("#frmMain_so_EmployerCountry").attr("value", null);
        $("#frmMain_so_HoldAddress1").attr("value", null);
        $("#frmMain_so_HoldAddress2").attr("value", null);
        $("#frmMain_so_HoldAddress3").attr("value", null);
        $("#frmMain_so_HoldZipCode").attr("value", null);
        $("#frmMain_so_HoldTownCity").attr("value", null);
        $("#frmMain_so_HoldState").attr("value", null);
        $("#frmMain_so_HoldCountry").attr("value", null);
        $("#frmMain_so_Nationality").attr("value", null);
        $("#frmMain_so_CountryOfResidence").attr("value", null);
        $("#frmMain_so_residencePhoneNo").attr("value", null);
        $("#frmMain_so_MobilePhone").attr("value", null);
        $("#frmMain_so_CustEducation").attr("value", null);
        $("#frmMain_so_EmailAddress").attr("value", null);
        $("#frmMain_so_Gender").attr("value", null);
        $("#frmMain_so_MaritalStatus").attr("value", null);
        $("#frmMain_so_Staff").attr("value", null);
        $("#frmMain_so_EmployeeId").attr("value", null);
        $("#frmMain_so_BusinessType").attr("value", null);
        $("#frmMain_so_Profession").attr("value", null);
        $("#frmMain_so_JobTitle").attr("value", null);
        $("#frmMain_so_AOBusinesss").attr("value", null);
        $("#frmMain_so_AOOperation").attr("value", null);
        $("#frmMain_so_AliasName").attr("value", null);
        $("#frmMain_so_MotherMaidenName").attr("value", null);
        $("#frmMain_so_DebtType").attr("value", null);
        $("#frmMain_so_DebtStatus").attr("value", null);
        $("#frmMain_so_BranchLocationCode").attr("value", null);
        $("#frmMain_so_DistrictCode").attr("value", null);
        $("#frmMain_so_EmployeICCode").attr("value", null);
        $("#frmMain_so_ReliwthBank").attr("value", null);
        $("#frmMain_so_ConWithBank").attr("value", null);
        $("#frmMain_so_ResidenceType").attr("value", null);
        $("#frmMain_so_IDcardType").attr("value", null);
        $("#frmMain_so_ICExpiryDate").attr("value", null);
        $("#frmMain_so_BirthPlace").attr("value", null);
        $("#frmMain_so_MonthLyIncome").attr("value", null);
        $("#frmMain_so_DataTransXtractFlag").attr("value", null);
        $("#frmMain_so_HomeStatus").attr("value", null);
        $("#frmMain_so_NoOfDependant").attr("value", null);
        $("#frmMain_so_EmployerName").attr("value", null);
        $("#frmMain_so_EmployementDetails").attr("value", null);
        $("#frmMain_so_Occupation").attr("value", null);
        $("#frmMain_so_IncomeTaxNo").attr("value", null);
        $("#frmMain_so_RegistrationNo").attr("value", null);
        $("#frmMain_so_RegistrationDate").attr("value", null);
        $("#frmMain_so_BusinessCommendate").attr("value", null);
        $("#frmMain_so_BusinessLicenseNo").attr("value", null);
        $("#frmMain_so_PlaceOfInc").attr("value", null);
        $("#frmMain_so_AdditionalField1").attr("value", null);
        $("#frmMain_so_AdditionalField2").attr("value", null);
        $("#frmMain_so_AdditionalField3").attr("value", null);
        $("#frmMain_so_AdditionalField4").attr("value", null);
        $("#frmMain_so_AdditionalField5").attr("value", null);
        $("#frmMain_so_AdditionalField6").attr("value", null);
        $("#frmMain_so_AdditionalField7").attr("value", null);
        $("#frmMain_so_AdditionalField8").attr("value", null);
        $("#frmMain_so_AdditionalField9").attr("value", null);
        $("#frmMain_so_AdditionalField10").attr("value", null);
        $("#frmMain_so_typeOfCompany").attr("value", null);
        $("#frmMain_so_RegLuckyFlag").attr("value", null);
        $("#frmMain_so_BranchCodeCasa").attr("value", null);
        $("#frmMain_so_LOB").attr("value", null);
        $("#frmMain_so_AccessCodeCasa").attr("value", null);
        $("#frmMain_so_ProductCode").attr("value", null);
        $("#frmMain_so_custId").attr("value", null);
        $("#frmMain_so_AcctNo").attr("value", null);
        $("#frmMain_so_AccountOpenDate").attr("value", null);
        $("#frmMain_so_AdditionalRemarks").attr("value", null);
        $("#frmMain_so_AoBusiness").attr("value", null);
        $("#frmMain_so_AoOperation").attr("value", null);
        $("#frmMain_so_CurrentAccBiCode").attr("value", null);
        $("#frmMain_so_SavingAccBiCode").attr("value", null);
        $("#frmMain_so_OtherAccBiCode").attr("value", null);
        $("#frmMain_so_TdAccBiCode").attr("value", null);
        $("#frmMain_so_AdditionalField1Casa").attr("value", null);
        $("#frmMain_so_AdditionalField2Casa").attr("value", null);
        $("#frmMain_so_AdditionalField3Casa").attr("value", null);
        $("#frmMain_so_AdditionalField4Casa").attr("value", null);
        $("#frmMain_so_AdditionalField5Casa").attr("value", null);
        $("#frmMain_so_AdditionalField6Casa").attr("value", null);
        $("#frmMain_so_AdditionalField7Casa").attr("value", null);
        $("#frmMain_so_AdditionalField8Casa").attr("value", null);
        $("#frmMain_so_AdditionalField9Casa").attr("value", null);
        $("#frmMain_so_AdditionalField10Casa").attr("value", null);
        $("#frmMain_so_FlagCIF").attr("value", null);
    }
    jQuery(document).ready(function() {
        /* $("#rbBS").html("&nbsp;"); */
        $(window).resize();
        rdb.resetDisabled();
        rdb.resetReadonly();
        $("#spinner").addClass("ui-helper-hidden");
        //alert("BUTTON CLICK");
        rdb.setDisableButton("btnFind", "0111");
        rdb.setOnChange(function() {
            changeAction("frmMain", "26005", "_execute");

        },
        null,
        null,
        null,
        function(removeMsg) {
            //alert("BUTTON CLICK2");
			
            if (removeMsg) {
                //alert("BUTTON CLICK3");
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
                $("#actionMessage").remove();
                $("#spinner").addClass("ui-helper-hidden");
                $("#errMsg").html("");
            }
        }
    );
        $('#rbBS-1').click(function() {
            $("#rbBS-1").attr("checked", false).button("refresh"); 
            //alert("REFRESH ALL");
            $("#actionMessage").remove();
            $("#actionError").remove();
            $("span.errorMessage").remove();
            $("label").removeClass("errorLabel");
            $("#actionMessage").remove();
            $("#spinner").addClass("ui-helper-hidden");
            $("#errMsg").html("");
            $("#divwaitingMessage").dialog("close");
            $("#frmGo_idMenu").val("26301");
            $("#frmGo_goAction").val("exec");
            $("#btnGo").click();
			
        });
        rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
    });
</script>