<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<s:set var="noCard_" scope="request">${param.noCard}</s:set>
<s:set var="flagCard_" scope="request">${param.flagCard}</s:set>

<s:form id="tempFrmDlgCasaInq" action="dlg">
    <s:hidden name="dialog" value="dlgExistingAccount" />
    <s:hidden name="dlgcifNo" />
    <s:token name="dialogToken" />
</s:form>
<sj:a id="tempDlgInq" formIds="tempFrmDlgCasaInq" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:url var="urlGridCard" action="2630103_list" namespace="/json" escapeAmp="false">
    <%-- <s:param name="cardNo">${param.cardNo}</s:param> --%> <%-- request parameter cardNo --%>
</s:url>

<s:url var="urlNext" action="26301_exec" namespace="/json" escapeAmp="false">
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
    id="gridtableCard" 
 caption="Search Result for Card ATM"
    title="Search Result for Card ATM"
    dataType="local" 
    href="%{urlGridCard}"
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
    onSelectRowTopics="selectRow3" onGridCompleteTopics="gridCompleted3"
    navigatorExtraButtons="{
    casaOpening: {
    title: 'Opening New Account',
    caption: 'Account Creation',
    icon: 'ui-icon-document',
    topic: 'casaOpening',
	id: 'acctOpeningNew'
    },
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
        $("#gridtableCard_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
        $("#gridtableCard_pager_left div.ui-pg-div:contains('Account Creation')").hide();
        $("#gridtableCard").unsubscribe("selectRow3");
        $("#gridtableCard").subscribe("selectRow3", function(event, data) {       
            $("#tempFrmDlgCasaInq_dlgcifNo").val($(data).jqGrid("getCell", event.originalEvent.id, "custId"));
			$("#gridtableCard").data("custId",$(data).jqGrid("getCell", event.originalEvent.id, "custId"));
            $("#gridtableCard").data("fullName",$(data).jqGrid("getCell", event.originalEvent.id, "fullName"));
            $("#gridtableCard").data("nik" ,$(data).jqGrid("getCell", event.originalEvent.id, "nik"));
            
            $("#gridtableCard_pager_left #note").hide();
	    
	    $("#gridtableCard_pager_left div.ui-pg-div:contains('Account Inquiry')").show();
            $("#gridtableCard_pager_left div.ui-pg-div:contains('Account Creation')").show();
        });
        
        $("#gridtableCard").unsubscribe("gridCompleted3");
        $("#gridtableCard").subscribe("gridCompleted3", function(event, data) {
            
            if ($("#gridtableCard_pager_left #note").length == 0) {
                var recordTable=$('#gridtableCard').jqGrid('getGridParam', 'records');

                if (recordTable == 0){
                    $("#gridtableCard_pager_left").append("<span id='note' style='color:red'>No records found</span>");
                    $("#gridtableCard_pager_left div.ui-pg-div:contains('Account Creation')").hide();
                    $("#gridtableCard_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
					$('#btnNext').show();	
                } else {
					$("#gridtableCard_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
					//$("#btnNext").attr('disabled', 'true');
					$("#btnNext").hide();
                }                 
            } 
            var gC = "2";
            $("#frmMain_gridClear").val(gC.toString());
        });
        $("#gridtableCard").unsubscribe("casaOpening");
        $("#gridtableCard").subscribe("casaOpening", function(event, data) {
            
			var fullName = $("#gridtableCard").jqGrid("getCell",$("#gridtableCard").jqGrid("getGridParam","selrow"),"fullName");
			var cifNumber = $("#gridtableCard").jqGrid("getCell",$("#gridtableCard").jqGrid("getGridParam","selrow"),"custId");
			//alert(cifNumber);
            if (fullName == null){
                alert("Please select one of the record to Create Account"); 
            } else {
                var flags = "1";
                var newCIF = true;
                var flagSt = "2";
                result = true;
                flagAuth = "N";
                if(result == true){
					$('#frmMain_cifNo').val(cifNumber);
                    $('#frmMain_inputNik').val($('#frmMain_noKartuIden').val());
                    //dlgParams.event = event;
                    $("#frmMain_flagButton").val(flags.toString());
                    $("#frmMain_state").val(flagSt.toString());
                    $("#frmMain_so_flagCIF").val(newCIF);
					//alert("Click Next");
                    $("#btnNext").click();
					
					$("#gridtableCard_pager_left div.ui-pg-div:contains('Account Creation')").hide();
					$("#gridtableCard_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
                }
                //alert("CLICKnext");
            }
        });
        
        $("#gridtableCard").unsubscribe("casaInquiry");
        $("#gridtableCard").subscribe("casaInquiry", function(event, data) {
			var fullName = $("#gridtableCard").jqGrid("getCell",$("#gridtableCard").jqGrid("getGridParam","selrow"),"fullName");
			var cifNumber = $("#gridtableCard").jqGrid("getCell",$("#gridtableCard").jqGrid("getGridParam","selrow"),"custId");
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
        if ("<s:property value='%{#request.noCard_}' />" == '-1');
        else {
			//alert('tab1');
            $("#gridtableCard").jqGrid('setGridParam', {
                datatype: 'json',
                postData: {
                     'noCard': function() {return '<s:property value="%{#request.noCard_}" />';}                 
                }
            });
            $("#gridtableCard").trigger('reloadGrid');
            //$("#gridtableCard").trigger('reloadGrid', [{current: true}]);
        }
    });
</script>
