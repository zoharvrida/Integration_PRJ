<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="13301E_list" namespace="/json">
    <s:param name="namschedProfile"><%=request.getParameter("namschedProfile")%></s:param>     
</s:url>
<sjg:grid
    id="gridtable"
    caption=""
    dataType="json"
    href="%{urlGrid}"
    pager="true"
    pagerPosition="right"
    gridModel="gridTemplate"
    rowNum="15"
    rownumbers="true"
    altRows="true"
    autowidth="true"
    navigator="false"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridComplete"
>
    <sjg:gridColumn name="fixSchedulerPK.idScheduler" index="fixSchedulerPK.idScheduler" title="scheduler ID" sortable="false" width="50" hidden="true"/>
    <sjg:gridColumn name="fixSchedulerPK.namScheduler" index="fixSchedulerPK.namScheduler" title="scheduler Name" sortable="false" width="50"/>
    <sjg:gridColumn name="fixSchedulerPK.idTemplate" index="fixSchedulerPK.idTemplate" title="template ID" sortable="false" width="50" hidden="true"/>
    <sjg:gridColumn name="flgStatus" index="flgStatus" title="flagStatus" sortable="false" width="50" hidden="true"/>
    
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {

            $("#" + dlgParams.idschedProfile).val($(data).jqGrid("getCell", event.originalEvent.id, "fixSchedulerPK.idScheduler"));
            $("#" + dlgParams.namschedProfile).val($(data).jqGrid("getCell", event.originalEvent.id, "fixSchedulerPK.namScheduler"));
            $("#" + dlgParams.idFixTemplate).val($(data).jqGrid("getCell", event.originalEvent.id, "fixSchedulerPK.idTemplate"));
            $("#" + dlgParams.flgStatus).val($(data).jqGrid("getCell", event.originalEvent.id, "flgStatus"));
            
            //alert("lest test");
            if (dlgParams.onClose != undefined) {
                dlgParams.onClose();
            }
            $("#ph-dlg").dialog("close");
            if(rdb.current == "edit"){
               $("#choiceAction").buttonset("enable");
               $("#choiceAction").button("enable");   
            }
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 650), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 650)-22);
        });
    });
</script>
