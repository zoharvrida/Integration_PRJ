<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateHome" action="12202" />
<sj:a id="home" href="%{ajaxUpdateHome}" name="home" cssClass="ui-helper-hidden"></sj:a>
<s:form id="frmDownload" name="frmDownload" action="12202_exec.action" method="post">
    <s:hidden name="filePath"/>
    <s:hidden name="fileName"/>
    <s:hidden name="reason"/>
    <s:hidden name="idBatch"/>
    <s:hidden name="idReport"/>
    <s:hidden name="usertemp" value="%{#session.namUser}" />
    <sj:submit
        id="btnSave"
        name="btnSave"
        formIds="frmDownload"
        disabled="false"
        targets="" 
        />
    <s:token />
</s:form>
<sj:a id="tempDownload" href="%{formDownload}" targets="ph-main" cssClass="ui-helper-hidden"></sj:a>
<script type="text/javascript">
    $('#submission').attr('style','display:none');   
</script>
<s:url var="urlGrid" action="12202_list" namespace="/json" escapeAmp="false" />

<s:actionerror name="actionError" />
<sjg:grid
    id="gridtable2"
    name="gridtable2"
    caption=""
    dataType="local"
    href="%{urlGrid}"
    pager="true"
    pagerPosition="right"
    gridModel="gridTemplate"
    cssStyle="cursor:pointer"
    loadonce="false" 
    rowNum="20"
    rownumbers="true"
    altRows="true"
    autowidth="true"
    navigator="true"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
    multiselect="false"
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridComplete"
	navigatorExtraButtons="{
    refreshDL: {
    title: 'Refresh',
    caption: '',
    icon: 'ui-icon-refresh',
    topic: 'refreshDL'
    },
    alreadyDL: {
    title: 'Downloaded',
    caption: '',
    icon: 'ui-icon-document',
    topic: 'alreadyDL'
    }
    }"
    >
    <sjg:gridColumn name="idReport" index="idReport" title="REPORT ID" sortable="false" hidden="true" />
    <sjg:gridColumn name="tempStat.nameFile" index="tempStat.nameFile" title="REPORT NAME" sortable="false" hidden="false" width="500"/>
    <sjg:gridColumn name="filePath" index="filePath" title="FILE PATH" sortable="true" hidden="true" />
    <sjg:gridColumn name="dtmRequest" index="dtmRequest" title="DTMREQUEST" sortable="true" formatter="date" align="left"
                    formatoptions="{newformat : 'd/m/Y h:i:s', srcformat : 'Y-m-d H:i:s'}" width="120" />
    <sjg:gridColumn name="status" index="status" title="REPORT STATUS" sortable="false" width="200" />
    <sjg:gridColumn name="tempStat.reason" index="tempStat.reason" title="REASON" sortable="false" hidden="true"/>
    <sjg:gridColumn name="compositeId.idBatch" index="compositeId.idBatch" title="ID BATCH" sortable="false" hidden="true"/>
</sjg:grid>
<script type="text/javascript"> 
    $('#btnSave').attr('style','display:none');
    var arrayClick;
    $(function() {
        //$("#" + rowId).find('td').eq('6').html('Download');
        $("#gridtable2").unsubscribe("selectRow");
        $("#gridtable2").subscribe("selectRow", function(event, data) {
            //alert("test");
            $("#filePath").val($(data).jqGrid("getCell", event.originalEvent.id, "filePath"));
            $("#dtmRequest").val($(data).jqGrid("getCell", event.originalEvent.id, "dtmRequest"));
            $("#status").val($(data).jqGrid("getCell", event.originalEvent.id, "status"));
            
            $("#frmDownload_reason").val($(data).jqGrid("getCell", event.originalEvent.id, "tempStat.reason"));
            $("#frmDownload_filePath").val($(data).jqGrid("getCell", event.originalEvent.id, "filePath"));
            $("#frmDownload_idBatch").val($(data).jqGrid("getCell", event.originalEvent.id, "compositeId.idBatch")); 
            $("#frmDownload_fileName").val($(data).jqGrid("getCell", event.originalEvent.id, "tempStat.nameFile"));
            $("#frmDownload_idReport").val($(data).jqGrid("getCell", event.originalEvent.id, "idReport"));
            //frmDownload.submit(); 
            var selr = jQuery('#gridtable2').jqGrid('getGridParam','selrow');
            //$('#'+ selr).hide();
            //alert(selr);
            
            if($("#status").val() == "Ready to download" || $("#status").val() == "Ready To Download") {
                $("#gridtable2").jqGrid('delRowData',selr);
                $("#btnSave").click();
			}
			else if($("#status").val() == "Download") {
                $("#btnSave").click();
                //$("#gridtable2").jqGrid('delRowData',selr);
            
            } else if($("#status").val() == "Error"){
                $("#gridtable2").jqGrid('delRowData',selr);
                // alert($("#frmDownload_reason").val());
                //$("#refresh_gridtable2").click();
                 $("#btnSave").click();
                //$('#'+ selr).hide();
            }
        });
		$("#gridtable2").unsubscribe("refreshDL");
        $("#gridtable2").subscribe("refreshDL", function(event, data) {
                $('#user').val($('#usertemp').val());
                var data = "";
            $('#tabbedpanel').tabs({
                ajaxOptions: {
                    type: 'POST',
                    data: {
                        'user': $("#frmMain_user").val(),
                        'flag': 1
                    }
                }
            });
                $('#tabbedpanel').tabs('load', 0);
                $("#tabbedpanel").tabs("option", "disabled", []);
        });
				$("#gridtable2").unsubscribe("alreadyDL");
        $("#gridtable2").subscribe("alreadyDL", function(event, data) {
                $('#user').val($('#usertemp').val());
                var data = $("#user").val();
                //var flag = $("#flag").val("1");
                $('#tabbedpanel').tabs({
                    ajaxOptions: {
                        type: 'POST',
                    data: {
                        'user': $("#frmMain_user").val(),
                        'flag': 2
                    }
                    }
                });
                $('#tabbedpanel').tabs('load', 1);
                $("#tabbedpanel").tabs("option", "disabled", []);
        });
        
        $("#gridtable2").jqGrid('setGridParam', {
            datatype: 'json',
            postData: {
                'action': 'grid',
                'user': '<s:property value="%{#parameters.user}" />',
                'flag': '<s:property value="%{#parameters.flag}" />'
            }
        });
        $("#gridtable2").trigger('reloadGrid');
    });
</script>
<%    }%>
