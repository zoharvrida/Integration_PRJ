<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<s:set var="customerName_" scope="request">${param.customerName}</s:set>
<s:set var="dateofBirth_" scope="request">${param.dateofBirth}</s:set>
<s:set var="flagCard_" scope="request">${param.flagCard}</s:set>
<s:set var="noKartuIden_" scope="request">${param.noKartuIden}</s:set>

<s:form id="tempFrmDlgCasaInq" action="dlg">
    <s:hidden name="dialog" value="dlgExistingAccount" />
    <s:hidden name="dlgcifNo" />
    <s:token name="dialogToken" />
</s:form>
<sj:a id="tempDlgInq" formIds="tempFrmDlgCasaInq" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:form id="tempFrmDlgCasaChnl" action="dlg">
    <s:hidden name="dialog" value="dlgChannelAccount" />
    <s:hidden name="dlgNIK" />
    <s:hidden name="dlgExt" />
    <s:hidden name="dlgNamCust" />
    <s:hidden name="dlgDateofBirth" />
    <s:hidden name="dlgCif" />
    <s:token name="dialogNikToken" />
</s:form>
<sj:a id="tempDlgChnl" formIds="tempFrmDlgCasaChnl" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

    
<s:url var="urlGrid" action="26301_list" namespace="/json" escapeAmp="false">
    <%-- <s:param name="cardNo">${param.cardNo}</s:param> --%> <%-- request parameter cardNo --%>
</s:url>

<s:url var="urlNext" action="26301_exec" namespace="/json" escapeAmp="false">
    <%-- <s:param name="cardNo">${param.cardNo}</s:param> --%> <%-- request parameter cardNo --%>
</s:url>

<s:actionerror name="actionError" />

<sjg:grid 
    id="gridtableCIF" 
    caption="Search Result for Name and Date of Birth"
    title="Search Result for Name and Date of Birth"
    dataType="local" 
    href="%{urlGrid}"
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
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridCompleted1"
    navigatorExtraButtons="{
    casaOpening: {
    title: 'Opening New Account',
    caption: 'Account Creation',
    icon: 'ui-icon-document',
    topic: 'casaOpening',
    id: 'acctOpeningNew'
    },
    casaExtOpening: {
    title: 'Opening Account from Channel',
    caption: 'Account Channel Inquiry',
    icon: 'ui-icon-document',
    topic: 'casaExtOpening'
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
    <sjg:gridColumn name="extFlag" index="extFlag" title="SourceFlag" width="100" align="center" sortable="false" hidden="true" />

</sjg:grid>

<script type="text/javascript">
    jQuery(document).ready(function() {
        $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
        $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Creation')").hide();
        $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Channel Inquiry')").hide();
        
        $("#gridtableCIF").unsubscribe("selectRow");
        $("#gridtableCIF").subscribe("selectRow", function(event, data) {       
            
            var cifNumber = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"extFlag");
            if(cifNumber != "SSCASA"){
                $("#tempFrmDlgCasaChnl_dlgNIK").val($(data).jqGrid("getCell", event.originalEvent.id, "nik"));
                $("#tempFrmDlgCasaChnl_dlgExt").val($(data).jqGrid("getCell", event.originalEvent.id, "extFlag"));
                $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
                $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Creation')").hide();
            } else {
            $("#tempFrmDlgCasaInq_dlgcifNo").val($(data).jqGrid("getCell", event.originalEvent.id, "custId"));
                
                $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Inquiry')").show();
                $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Creation')").show();
            }
            
            $("#gridtableCIF").data("custId",$(data).jqGrid("getCell", event.originalEvent.id, "custId"));
            $("#gridtableCIF").data("fullName",$(data).jqGrid("getCell", event.originalEvent.id, "fullName"));
            $("#gridtableCIF").data("nik" ,$(data).jqGrid("getCell", event.originalEvent.id, "nik"));
            $("#gridtableCIF").data("extFlag" ,$(data).jqGrid("getCell", event.originalEvent.id, "extFlag"));
            
            $("#gridtableCIF_pager_left #note").hide();
            
            
            var flag = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"extFlag");
            if(flag != "SSCASA"){
                $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Channel Inquiry')").show();
            } else {
                $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Channel Inquiry')").hide();
            }
        });
        
        $("#gridtableCIF").unsubscribe("gridCompleted1");
        $("#gridtableCIF").subscribe("gridCompleted1", function(event, data) {
            $("#frmMain_recordsTable").val('1');
            if ($("#gridtableCIF_pager_left #note").length == 0) {
                var recordTable=$('#gridtableCIF').jqGrid('getGridParam', 'records');

                if (recordTable == 0){
                    $("#gridtableCIF_pager_left").append("<span id='note' style='color:red'>No records found</span>");
                    $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Creation')").hide();
                    $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
                    //$('#btnNext').show();
                } else {
                    $("#gridtableCIF_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
                }                 
            } 
            var gC = "2";
            $("#frmMain_gridClear").val(gC.toString());
            
            var flag1 = $("#frmMain_recordsTableNIK").val();
            var flag2 = $("#frmMain_recordsTable").val();

            if(flag1 == "1" && flag2 == "1"){
            
                var nikFlag = $('#gridtableNik').jqGrid('getGridParam', 'records');
                var cifFlag = $('#gridtableCIF').jqGrid('getGridParam', 'records');
                
                //alert("cif log\njml record nik:" + nikFlag + "\njml record CIF:" + cifFlag);
                
                if(nikFlag == 0 && cifFlag == 0){
                    $('#btnNext').show();
                } 
            }
        });
        $("#gridtableCIF").unsubscribe("casaOpening");
        $("#gridtableCIF").subscribe("casaOpening", function(event, data) {
            
                        var fullName = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"fullName");
            var cifNumber = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"custId");
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
                    $("#btnNext").click();
                    $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Creation')").hide();
                    $("#gridtableCIF_pager_left div.ui-pg-div:contains('Account Inquiry')").hide();
                }
                //alert("CLICKnext");
            }
        });
        
        $("#gridtableCIF").unsubscribe("casaExtOpening");
        $("#gridtableCIF").subscribe("casaExtOpening", function(event, data) {
            var fullName = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"fullName");
            var DateofBirth = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"dob");
            var nik = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"nik");
            var ext = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"extFlag");
            var cifNumber = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"custId");
            //alert(cifNumber);
            if (fullName == null){
                alert("Please select one of the record to Inquiry Channel Account"); 
            } else {
                $("#tempFrmDlgCasaChnl_dlgNIK").val(nik);
                $("#tempFrmDlgCasaChnl_dlgExt").val(ext);
                $("#tempFrmDlgCasaChnl_dlgDateofBirth").val(DateofBirth);
                $("#tempFrmDlgCasaChnl_dlgNamCust").val(fullName);
                $("#tempFrmDlgCasaChnl_dlgCif").val(cifNumber);
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
                    $("#tempDlgChnl").click();
                    //alert("DIALOG");
                })
                .dialog("open");
            }
        });
        
        $("#gridtableCIF").unsubscribe("casaInquiry");
        $("#gridtableCIF").subscribe("casaInquiry", function(event, data) {
                        var fullName = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"fullName");
            var cifNumber = $("#gridtableCIF").jqGrid("getCell",$("#gridtableCIF").jqGrid("getGridParam","selrow"),"custId");
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
        if ("<s:property value='%{#request.customerName_}' />" == '-1');
        else {
            //alert('tab1');
            $("#gridtableCIF").jqGrid('setGridParam', {
                datatype: 'json',
                postData: {
                    'customerName': function() {return "<s:property value='%{#request.customerName_}' />";},
                    'dateofBirth': function() {return '<s:property value="%{#request.dateofBirth_}" />';},
                    'noKartuIden':function() {return '<s:property value="%{#request.noKartuIden_}" />';}
                }
            });
            $("#gridtableCIF").trigger('reloadGrid');
            //$("#gridtableCIF").trigger('reloadGrid', [{current: true}]);
        }
    });
</script>
