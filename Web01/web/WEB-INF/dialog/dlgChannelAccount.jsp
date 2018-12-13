<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:set var="nik_" scope="request">${param.dlgNIK}</s:set>
<s:set var="extFlag_" scope="request">${param.dlgExt}</s:set>
<s:set var="name_" scope="request">${param.dlgNamCust}</s:set>
<s:set var="dob_" scope="request">${param.dlgDateofBirth}</s:set>
<s:set var="cif_" scope="request">${param.dlgCif}</s:set>

<s:url var="urlGridExist" action="26301_list" namespace="/json" escapeAmp="false">
    
</s:url>

<s:actionerror name="actionError" />
<sjg:grid
    id="gridTableChannel"
    caption="Pre-Creation Account by Channel"
    dataType="local"
    href="%{urlGridExist}"
    requestType="POST"
    pager="true"
    pagerPosition="right"
    gridModel="gridTemplate"
    rowNum="15"
    rownumbers="true"
    altRows="true"
    autowidth="true"
    navigator="true"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
    onSelectRowTopics="selectRowChannel" onGridCompleteTopics="gridCompleteChannel"
    navigatorExtraButtons="{
    casaChannelOpening: {
    title: 'Opening New Account for Channel',
    caption: 'Account Creation for Channel',
    icon: 'ui-icon-document',
    topic: 'casaChannelOpening'
    }
    }"
    >
    <sjg:gridColumn name="productCode" index="productCode" title="Product Code" sortable="false" width="30"/>
    <sjg:gridColumn name="productCodeDesc" index="productCodeDesc" title="Product Name" sortable="false" width="120"/>
    <sjg:gridColumn name="branchCode" index="branchCode" title="Home Branch" sortable="false" width="30"/>
    <sjg:gridColumn name="acctTitle" index="acctTitle" title="Account Name" sortable="false" width="30"/>
    <sjg:gridColumn name="so.dtmCreated" index="so.dtmCreated" title="Registration Date" sortable="false" width="100" hidden="true"/>
    <sjg:gridColumn name="extUser" index="extUser" title="Channel Name" sortable="false" width="100" hidden="true"/>
    <sjg:gridColumn name="applicationID" index="applicationID" title="Application ID" sortable="false" width="100" hidden="true"/>
    <sjg:gridColumn name="cifNumber" index="cifNumber" title="CIF Number" sortable="false" width="100" hidden="true"/>
</sjg:grid>
<script type="text/javascript">
    jQuery(document).ready(function()  {
        //alert("TESTING");
        $("#gridTableChannel_pager_left div.ui-pg-div:contains('Account Creation for Channel')").hide();
        
        $("#gridTableChannel").unsubscribe("selectRowChannel");
        $("#gridTableChannel").subscribe("selectRowChannel", function(event, data) {
            var acctTitle = $("#gridTableChannel").jqGrid("getCell",$("#gridTableChannel").jqGrid("getGridParam","selrow"),"acctTitle");
            //alert(acctTitle);
            if(acctTitle == null){
                alert("Please select one of the record to Create Account Via Channel"); 
                $("#gridTableChannel_pager_left div.ui-pg-div:contains('Account Creation for Channel')").hide();
            } else {
                $("#gridTableChannel_pager_left div.ui-pg-div:contains('Account Creation for Channel')").show();
            }
            $("#gridTableChannel_pager_left #note").hide();
        });
		
        $("#gridTableChannel").unsubscribe("gridCompleteChannel");
        $("#gridTableChannel").subscribe("gridCompleteChannel", function(event, data) {
            //$("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 400), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({width: Math.min(screen.availWidth - 20, 850), height: $("#gbox_gridTableChannel").height() + $("div.ui-dialog-titlebar.ui-widget-header").height() + 44});
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth - 20, 850) - 22);
            
            if ($("#gridTableChannel_pager_left #note").length == 0) {
                var recordTable=$('#gridTableChannel').jqGrid('getGridParam', 'records');

                if (recordTable == 0){
                    $("#gridTableChannel_pager_left").append("<span id='note' style='color:red'>No records found</span>");
                    $("#gridTableChannel_pager_left div.ui-pg-div:contains('Account Creation for Channel')").hide();
                } else {
                    $("#gridTableChannel_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
                }                 
            } 
        });
        //$("#gridTableChannel").trigger('reloadGrid');
        $("#gridTableChannel").unsubscribe("casaChannelOpening");
        $("#gridTableChannel").subscribe("casaChannelOpening", function(event, data) {
            var acctTitle = $("#gridTableChannel").jqGrid("getCell",$("#gridTableChannel").jqGrid("getGridParam","selrow"),"acctTitle");
            var extProfile = $("#gridTableChannel").jqGrid("getCell",$("#gridTableChannel").jqGrid("getGridParam","selrow"),"extUser");
            var appID = $("#gridTableChannel").jqGrid("getCell",$("#gridTableChannel").jqGrid("getGridParam","selrow"),"applicationID");
            var cifNumber = $("#gridTableChannel").jqGrid("getCell",$("#gridTableChannel").jqGrid("getGridParam","selrow"),"cifNumber");
            if(acctTitle == null){
                alert("Please select one of the record to Create Account Via Channel"); 
            } else {
                var flags = "1";
                var newCIF = false;
                var flagSt = "1";
                result = true;
                if(result == true){
                    $('#frmMain_inputNik').val($('#frmMain_noKartuIden').val());
                    //dlgParams.event = event;
                    $("#frmMain_flagButton").val(flags.toString());
                    $("#frmMain_state").val(flagSt.toString());
                    $("#frmMain_so_flagCIF").val(newCIF);
                    $("#frmMain_so_extUser").val(extProfile);
                    $("#frmMain_so_applicationID").val(appID);
                    $("#frmMain_cifNo").val(cifNumber);
                    $("#btnNext").click();
                    $("#gridTableChannel_pager_left div.ui-pg-div:contains('Account Creation for Channel')").hide();
                    $("#ph-dlg").dialog("close");
                }
            }
        });
        $("#gridTableChannel").jqGrid('setGridParam', {
            datatype: 'json',
            postData: {
                'customerName': function() {return '<s:property value="%{#request.name_}" />';},
                'dateofBirth': function() {return '<s:property value="%{#request.dob_}" />';},
                'noKartuIden':function() {return '<s:property value="%{#request.nik_}" />';},
                'extFlag':function() {return '<s:property value="%{#request.extFlag_}" />';},
                'cifNo':function() {return '<s:property value="%{#request.cif_}" />';
                }
            }
        });
        $("#gridTableChannel").trigger('reloadGrid');
    });
</script>
<%
    }%>