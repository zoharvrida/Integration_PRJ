<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="noKartuIden_" scope="request">${param.noKartuIden}</s:set>
<s:set var="flagCard_" scope="request">${param.flagCard}</s:set>
<s:set var="btnNext_" scope="request">${param.btnNext}</s:set>
<%-- Revision : Add Button Inquiry Change : 11-Januari-2016 Changer : v00020800 Begin 1 --%>
<s:form id="tempFrmDlgCasaInq" action="dlg">
    <s:hidden name="dialog" value="dlgExistingAccount" />
    <s:hidden name="dlgcifNo" />
    <s:token name="dialogToken" />
</s:form>
<sj:a id="tempDlgInq" formIds="tempFrmDlgCasaInq" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<%--<s:set var="dateofBirth" scope="request">${param.dateofBirth}</s:set>--%>

<s:url var="urlGridNik" action="2630102_list" namespace="/json" escapeAmp="false">
    <%-- <s:param name="cardNo">${param.cardNo}</s:param> --%> <%-- request parameter cardNo --%>
</s:url>

<s:actionerror name="actionError" />

<script>
    function statusFormatter(cellValue, options, rowObject) {
        var result = "-";
        var status = rowObject.tabCasastatusnik;

        /*
         if (status == '0')
         result = "Not active";
         else if (status == '1')
         result = "Open";
         else if (status == '9')
         result = "Closed";
         else
         */
        result = status;

        return result;
    }
    ;
</script>

<sjg:grid 
    id="gridtableNik" 
    caption="Search Result for NIK"
    title="Search Result for NIK"
    dataType="local" 
    href="%{urlGridNik}"
    requestType="POST"
    pager="true"
    pagerPosition="right"
    gridModel="gridTemplate" 
    rowNum="15"
    rownumbers="false"
    altRows="true" 
    autowidth="true"
    navigator="true"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
	onSelectRowTopics="selectRow2" 
    onGridCompleteTopics="gridCompleted2"
	 navigatorExtraButtons="{    
    casaInquiry: {
    title: 'Inquiry Existing Account',
    caption: 'Account Inquiry',
    icon: 'ui-icon-document',
    topic: 'casaInquiry'
    }
    }"
    >
    <sjg:gridColumn name="no" index="no" title="No. " width="50" align="center" sortable="false" />
    <sjg:gridColumn name="fullName" index="fullName" title="Full Name" width="100" align="center" sortable="false"  />
    <sjg:gridColumn name="address" index="address" title="Address" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="custId" index="custId" title="Customer Id" width="80" align="center" sortable="false" />
    <sjg:gridColumn name="nik" index="nik" title="No. Kartu Identitas" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="dob" index="dob" title="Date of Birth" width="100" align="center" sortable="false" formatter="date"
                    formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d'}" />

</sjg:grid>

<script type="text/javascript">
    jQuery(document).ready(function() {
	
	$("#gridtableNik_pager_left div.ui-pg-div:contains('Account Creation')").hide();
	$("#gridtableNik_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();  
	   $("#gridtableNik").unsubscribe("selectRow2");
       $("#gridtableNik").subscribe("selectRow2", function(event, data) {       
            $("#tempFrmDlgCasaInq_dlgcifNo").val($("#gridtableNik").jqGrid("getCell",$("#gridtableNik").jqGrid("getGridParam","selrow"),"custId"));
            
            $( "#gridtableNik_pager_left #note").hide();
			$("#gridtableNik_pager_left div.ui-pg-div:contains('Account Inquiry')").show();
           
        });
        $("#gridtableNik").unsubscribe("gridCompleted2");
		
        $("#gridtableNik").subscribe("gridCompleted2", function(event, data) {
			$("#frmMain_recordsTableNIK").val('1');
            if ($("#gridtableNik_pager_left #note").length == 0) {
                
                var recordTable=$('#gridtableNik').jqGrid('getGridParam', 'records');
				$("#gridtableNik_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
                if (recordTable == 0) {
                    $("#gridtableNik_pager_left").append("<span id='note' style='color:red'>No records found</span>");
					$("#gridtableNik_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
					//$("#frmMain_recordsTableNIK").val(0);
		  }else{
					$("#gridtableNik_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
                    //$("#btnNext").attr('disabled', 'true');
					//$("#frmMain_recordsTableNIK").val(recordTable);
					$("#btnNext").hide();
                }
            }
			var flag1 = $("#frmMain_recordsTableNIK").val();
            var flag2 = $("#frmMain_recordsTable").val();

            if(flag1 == "1" && flag2 == "1"){
			
				var nikFlag = $('#gridtableNik').jqGrid('getGridParam', 'records');
				var cifFlag = $('#gridtableCIF').jqGrid('getGridParam', 'records');
				
				//alert("NIK log\njml record nik:" + nikFlag + "\njml record CIF:" + cifFlag);
				if(nikFlag == 0 && cifFlag == 0){
					$('#btnNext').show();
				}
				/*if(nikFlag == '0' || nikFlag == null){
					if(cifFlag == '0' || cifFlag == null){
						alert("NIKNEXT");
						$('#btnNext').show();
					}
				} else {
					if(cifFlag != '0' || cifFlag != null){
						$('#btnNext').hide();
					}
				}*/
                
            }
        });
 $("#gridtableNik").unsubscribe("casaInquiry");
        $("#gridtableNik").subscribe("casaInquiry", function(event, data) {
                        var fullName = $("#gridtableNik").jqGrid("getCell",$("#gridtableNik").jqGrid("getGridParam","selrow"),"fullName");
			var cifNumber = $("#gridtableNik").jqGrid("getCell",$("#gridtableNik").jqGrid("getGridParam","selrow"),"custId");
			//alert("CIF "+ cifNumber);
            if (fullName == null){
                alert("Please select one of the record to Inquiry Account"); 
            } else {
                //alert("INQUIRY");

                dlgParams = {};
                dlgParams.onClose = function() {
                    //alert("test onclose");
                };
                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                .html("Please wait...")
                .unbind("dialogopen")
                .bind("dialogopen", function() {
                    $("#tempDlgInq").click();
                    //alert("DIALOG");
                })
                .dialog("open");
            }
        });
        if ("<s:property value='%{#request.noKartuIden_}' />" == '-1')
        ;
        else {
            $("#gridtableNik").jqGrid('setGridParam', {
                datatype: 'json',
                postData: {
                    
                    'noKartuIden': function() {return '<s:property value="%{#request.noKartuIden_}" />';

                    }
                }
            });
            var flags = "0";
            $("#frmMain_flagButton").val(flags.toString());
            $("#gridtableNik").trigger('reloadGrid');
            //$("#gridtableNik").trigger('reloadGrid', [{current: true}]);
        }
    });
</script>
