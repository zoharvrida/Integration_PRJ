<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjt" uri="/struts-jquery-tree-tags"%>

<script type="text/javascript">
    $(function() {
        $("#jsonTree").subscribe("treeClicked", function(event, data) {
            var item = event.originalEvent.data.rslt.obj;
            if(item.attr("href")=="#"){

            }
            else{
                $("#ph-title").html("&nbsp;");
                $("#ph-main").empty();
                $("#ph-buttons").html("&nbsp;");
                $("#ph-main").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:40px; height:40px; z-index:99' /></span>" + $("#ph-main").html());
                $("#ph-title").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:20px; height:20px; z-index:99' /></span>" + $("#ph-title").html());
                $("#ph-buttons").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:20px; height:20px; z-index:99' /></span>" + $("#ph-buttons").html());
                $("#frmMenu_idMenu").val(item.attr("id"));
                $("#btnMenu").click();
            }
        });
    });
</script>
<s:url var="treeDataUrl" action="menu_content" namespace="/json" />
<s:form id="frmMenu" action="menu_click" target="">
    <sjt:tree
        id="jsonTree"
        jstreetheme="default"
        openAllOnLoad="true"
        href="%{treeDataUrl}"
        onClickTopics="treeClicked" cssClass="ui-widget ui-widget-content" cssStyle="border:white solid 0px; background-color:transparent"
        />
    <s:hidden name="idMenu" />
    <sj:submit id="btnMenu" formIds="frmMenu" targets="ph-main" cssClass="ui-helper-hidden"/>
    <s:token />
</s:form>