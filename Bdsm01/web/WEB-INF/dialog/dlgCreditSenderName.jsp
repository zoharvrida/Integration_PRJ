<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:form id="formParameter" action="12201_parameter.action">
    <s:hidden name="idReport" />
    <s:hidden name="idUser" value="%{#session.idUser}"/>
    <s:hidden name="idTemplate" value="%{#session.idTemplate}"/>
    <s:hidden name="namUser" value="%{#session.namUser}"/>
    <s:hidden name="cdBranch" value="%{#session.cdBranch}"/>
    <s:hidden name="dtBusiness" value="%{#session.dtBusiness}" />
    <s:token />
</s:form>
<sj:a id="tempParameter" formIds="formParameter" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:url var="urlGrid" action="10304D_list" namespace="/json" escapeAmp="false">
    <s:param name="reportName"><%=request.getParameter("reportName")%></s:param>
    <s:param name="idTemplate"><%=request.getParameter("idTemplate")%></s:param>  
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
    <sjg:gridColumn name="IDREPORT" index="IDREPORT" title="Report Id" sortable="false" width="50" hidden="true"/>
    <sjg:gridColumn name="REPORTNAME" index="REPORTNAME" title="Report Name" sortable="false"/>
    <sjg:gridColumn name="IDSCHEDULER" index="IDSCHEDULER" title="Scheduler Id" sortable="false" width="50" hidden="true"/>
    <sjg:gridColumn name="REMARKS" index="REMARKS" title="remarks" sortable="false" width="50" hidden="true"/>

</sjg:grid>
<script type="text/javascript">
    var arrayLab = new Array();
    var arrayRegexp = new Array();
    var arrayMax = new Array();
    var arrayMandatory = new Array();
    var arrayFormat = new Array();
    var arraySession = new Array('cdBranch','idTemplate','namUser','idUser','dtBusiness');
    var maximumLength = 100;
    
    function SeparatorParameter(params)
    {
        var regEx = /;+/g;
        
        //params = params.replace(/;+/g,';');
        params = params.substr(0,params.length-1);
        var arrayParams = params.split(";");
        var param = arrayParams.length;
        var incr = 0;
        //alert("Email validation COmplete");
        return arrayParams;
        //return param;
    }
    function getTextField(labelArray,maxlengthArray,mandatory,formating,regExp){
        var mainTab = document.getElementById("frmMain");
        var table = mainTab.getElementsByTagName('table')[1];
        var tRealBody = table.getElementsByTagName('tbody')[0];
        $("#resultS").val("");
        
        var hiddenparam = new Array();
        var k=0;
        for (var i = 0; i < labelArray.length; i++) {
            
                var row = document.createElement("tr");
                row.setAttribute("id", i);
                var cell = document.createElement("td");
                var label = document.createElement("td");
                var spanSite = document.createElement("span");
                var spanSep = document.createElement("span");

                label.setAttribute("class", "tdLabel");
                label.setAttribute("style", "vertical-align:middle");
                if(maxlengthArray > maximumLength){
                   var textfield = document.createElement("textarea");
                   textfield.setAttribute("cols", 120);
                   textfield.setAttribute("rows", 5);
                   
                } else {
                var textfield = document.createElement("input");
                   textfield.setAttribute("size", maxlengthArray[i]);
                   textfield.setAttribute("maxlength", maxlengthArray[i]);
                }
  
                var markparam = document.createElement("input");
                var labelparam = document.createElement("input");            
                var errorState = document.createTextNode("*");
                var separator = document.createTextNode(":");
                
            
                spanSite.setAttribute("class","required");
                spanSite.setAttribute("id","spans"+i);
            
                markparam.setAttribute("id","mark"+i);
                markparam.setAttribute("size", "1");
                markparam.setAttribute("maxlength", "2");
                markparam.setAttribute("readonly", "true");
                if(mandatory[i] == "Y"){
                    spanSite.appendChild(errorState);
                    spanSep.appendChild(separator);
                } else if(mandatory[i] == "N"){
                    spanSep.appendChild(separator);
                }
                textfield.setAttribute("id","parameter"+i);
                textfield.setAttribute("class","ui-widget ui-widget-content");
                textfield.setAttribute("size", maxlengthArray[i]);
                textfield.setAttribute("maxlength", maxlengthArray[i]);
                if(formating[i] == "Password"){
                    textfield.setAttribute("type", "password");
                }
            
                labelparam.setAttribute("id","labelparam"+i);
                labelparam.setAttribute("value",labelArray[i]);  
                labelparam.setAttribute("readonly", "true");
                labelparam.setAttribute("class","label");
                labelparam.setAttribute("align","right");

                if(mandatory[i] == "H") {
                   var arrayParam = new Array(); 
                   textfield.setAttribute("style","display:none");
                   labelparam.setAttribute("style","display:none");
                   markparam.setAttribute("style","display:none");
                   for(var m=0;m<arraySession.length;m++){
                     if(formating[i] == arraySession[m]){
                       //alert(regExp[i]);  
                       arrayParam[m] = regExp[i];
                     }   
                   }
                   if(arrayParam.length != 0){
                       hiddenparam[k] = regExp[i];
                       k++;
                       $("#resultS").val(JoinString(hiddenparam));
                   }
                }
                row.appendChild(label);
                label.appendChild(labelparam);
                label.appendChild(spanSite);
                label.appendChild(spanSep);
                cell.appendChild(textfield);
                row.appendChild(cell);
                tRealBody.appendChild(row);    
        }
        //mainTab.appendChild(fieldSet);
        //tableField.appendChild(tableBody);
        for(var t = 0;t<labelArray.length;t++)
        {   
            document.getElementById("labelparam"+t).style.background = "transparent";
            document.getElementById("labelparam"+t).style.border = "0px";
            document.getElementById("labelparam"+t).style.textAlign = "right"; 
            document.getElementById("labelparam"+t).style.fontSize = "11px";
        }
    }
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.idReport).val($(data).jqGrid("getCell", event.originalEvent.id, "IDREPORT"));
            $("#" + dlgParams.reportName).val($(data).jqGrid("getCell", event.originalEvent.id, "REPORTNAME"));
            $("#" + dlgParams.idScheduler).val($(data).jqGrid("getCell", event.originalEvent.id, "IDSCHEDULER"));
            $("#" + dlgParams.remarks).val($(data).jqGrid("getCell", event.originalEvent.id, "REMARKS"));
            //alert("lewat sini??")            
            if (dlgParams.onClose != undefined) {
                dlgParams.onClose();
            }
            labelParams = {};
            labelParams.idReport = $(data).jqGrid("getCell", event.originalEvent.id, "IDREPORT");
            labelParams.onClose = function(parameter,format,mandatory,maxLength,regexp){
                
                if(parameter == ""){
                    $("#params").val("");
                    e.preventDefault();
                } else {
                   
                    $("#params").val(parameter);
                    $("#format").val(format);
                    $("#mandatory").val(mandatory);
                    $("#maxLength").val(maxLength);
                    $("#regexp").val(regexp);
                }
                
                arrayLab = SeparatorParameter(parameter);
                arrayMax = SeparatorParameter(maxLength);
                arrayMandatory = SeparatorParameter(mandatory);
                arrayFormat = SeparatorParameter(format);
                arrayRegexp = SeparatorParameter(regexp);
                var counter = 0;
                for(var i = 0; i < arrayMandatory.length; i++){
                    if(arrayMandatory[i] != "H"){
                        counter++;
                    }
                }
                //arrayLab = MandatoryValidation(arrayLab,arrayMandatory);
                getTextField(arrayLab,arrayMax,arrayMandatory,arrayFormat,arrayRegexp);
                $("#dummy").val(arrayLab.length);
                $("#dummyNoHide").val(counter);
                counter = 0;
            }
            $("#formParameter_idReport").val($(data).jqGrid("getCell", event.originalEvent.id, "IDREPORT"));
            
            $("#tempParameter").click();
            $("#ph-dlg").dialog("close");

        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 650), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 650)-22);
        });
    });
</script>
