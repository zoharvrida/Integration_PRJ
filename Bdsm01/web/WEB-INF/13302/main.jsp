<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="13302_title" />
<s:url var="ajaxUpdateButtons" action="13302_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formRequester" action="13302_requester.action">
    <s:hidden name="gRPID" />
    <s:hidden name="schedImport" />
    <s:token />
</s:form> 
<sj:a id="tempRequester" formIds="formRequester" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formSupervisor" action="13302_supervisor.action">
    <s:hidden name="gRPID" />
    <s:hidden name="schedImport" />
    <s:token />
</s:form> 
<sj:a id="tempSupervisor" formIds="formSupervisor" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formMonitoring" action="13302_monitoring.action">
    <s:hidden name="gRPID" />
    <s:hidden name="schedImport" />
    <s:token />
</s:form> 
<sj:a id="tempMonitoring" formIds="formMonitoring" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="13302" />
    <s:token />
</s:form> 
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="13302" method="post" validate="true">
    <table id="accessTab">
        <s:url id="getListType" action="json/13302_getListType"/>
        <sj:select
            href="%{getListType}"
            id="gRPID"
            name="gRPID"
            list="grpiDList"
            listKey="GRPID"
            listValue="GRPID"
            onChangeTopics="schedImportList"
            onSelectTopics="schedImportList"
            cssClass="ui-widget ui-widget-content"
            key="label.idGrup"
            emptyOption="true"
            headerKey="-1"
            />
        <sj:select
            href="%{getListType}"
            id="schedImport"
            name="schedImport"
            list="schedImportList"
            listKey="IDSCHEDULER"
            listValue="NAMSCHEDULER"
            reloadTopics="schedImportList"
            onChangeTopics="tempID2List"
            cssClass="ui-widget ui-widget-content"
            key="label.schedProfile"
            emptyOption="true"
            headerKey="-1"
            disabled="false"
            />
        <sj:select
            href="%{getListType}"
            id="tempID2"
            name="tempID2"
            list="tempID2List" 
            listKey="index"
            listValue="value"
            reloadTopics="tempID2List"
            cssClass="ui-widget ui-widget-content"
            emptyOption="true"
            headerKey="-1"
            />  
        <tr id="s0">
            <sj:textarea
                id="requester"
                name="requester"
                requiredLabel="true"
                cssClass="ui-widget ui-widget-content"
                key="label.requester"
                disabled="false"
                autocomplete="off" 
                cols="140" rows="5"
                />
        </tr>
        <tr id="s1">
            <sj:textarea
                id="supervisor"
                name="supervisor"
                requiredLabel="true"
                cssClass="ui-widget ui-widget-content"
                key="label.Supervisor"
                disabled="false"
                autocomplete="off"
                cols="140" rows="5"
                />
        </tr>
        <tr id="s2">
            <sj:textarea
                id="monitoring"
                name="monitoring"
                requiredLabel="true"
                cssClass="ui-widget ui-widget-content"
                key="label.Monitoring"
                disabled="false"
                autocomplete="off"
                cols="140" rows="5"
                />
        </tr>
    </table>

    <s:hidden name="gRPIDname"/>
    <s:hidden name="validatereq" id="validatereq" />
    <s:hidden name="validatesup" id="validatesup" />
    <s:hidden name="validatemon" id="validatemon" />
    <s:hidden name="idTemplate" value="%{#session.idTemplate}"/>
    <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" />
    <sj:submit
        id="btnSave"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.save"
        disabled="true"
        targets="ph-main" 
        onBeforeTopics="beforeSubmit"
        />
    <s:token />
</s:form>
<script type="text/javascript">
    $('#tempID2').attr('style','display:none');
    $('.tdLabel').attr('style','vertical-align:middle');
    // manual required
    var labelReq = document.getElementsByTagName("label")[3];
    var spanSite = document.createElement("span");
    var errorState = document.createTextNode("*");
    spanSite.setAttribute("class","required");
    spanSite.appendChild(errorState);
    labelReq.appendChild(spanSite);
    
    var labelSup = document.getElementsByTagName("label")[4];
    var spanSite2 = document.createElement("span");
    var errorState2 = document.createTextNode("*");
    spanSite2.setAttribute("class","required");
    spanSite2.appendChild(errorState2);
    labelSup.appendChild(spanSite2);
    
    function removesRow2(rowid){
        var row =  document.getElementById(rowid);
        //alert(rowid);
        row.parentNode.removeChild(row);
    }
    function getStatusChosen() {
        if($("#frmMain_schedStatus").val() != '' && $("#frmMain_schedProfile").val() != '') {
            tempParams = {};
            tempParams.statusChosen = "frmMain_statusChosen";

            $("#formGetStatus_schedProfile").val($("#frmMain_schedProfile").val());
            $("#formGetStatus_typeS").val($("#frmMain_typeS").val());
            $("#tempGetStatus").click();
        }
    }
    function JoinAll(joinArray){
    
        var joinAll = joinArray.join(", ");
        joinAll = joinAll.replace(/,+/g,',');
        return joinAll;
    }
    function getTipeScheduler(id) {
        $("#formGetStatus_typeS").val(id);
        tempParams = {};
        tempParams.statusChosen = "formGetStatus_typeS";
    }
    function SeparatorRequest(requester,supervisor,monitoring,separator)
    {
        var regEx = /;+/g;
        var Emailreg = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
        
        var emptyReq = false;
        var summary = 0;
        
        if(requester != ""){
            requester = requester.replace(/;+/g,';');
            var Lreq = requester[requester.length-1];
            if(Lreq == ";"){
                var lengReq = requester.length-1;
                requester = requester.substr(0,lengReq);
                
            }
            var arrayRequest = requester.split(separator);
            var req = arrayRequest.length;
            var incr = 0;
            var r = 0;
            var reqNoEmail = 0;
            var noEMail = null;
            var reqSame = 0;
            var reqTempArray = new Array();
            
            for(var i= 0;i< req; i++){
                incr++;
                
                if(arrayRequest[i].match(Emailreg)){
                } else {
                    reqTempArray[r] = arrayRequest[i];
                    reqNoEmail = 1;
                }
                for(var j=incr;j< req; j++){
                    if(arrayRequest[i] == arrayRequest[j]){
                        
                        reqSame = 1;
                    }   
                }
                //req--;
            }
            noEMail = reqTempArray[0];
           
        } else {
            emptyReq = true;
        }
        
        if(emptyReq == true || reqNoEmail == 1 || reqSame == 1){
            
            summary++;
        }
        var emptySpv = false;
        if(supervisor != ""){
            supervisor = supervisor.replace(/;+/g,';');
            var Lsup = supervisor[supervisor.length-1];
            if(Lsup == ";"){
                var lengSup = supervisor.length-1;
                supervisor = supervisor.substr(0,lengSup);
                
            }
            var arraySupervisor = supervisor.split(separator);
            var req = arraySupervisor.length;
            var incr = 0;
            var spvNoEmail = 0;
            var s = 0;
            var noEMail2 = null;
            var spvSame = 0;
            var supTempArray = new Array();

            for(var i= 0;i< req; i++){
                incr++;
                if(arraySupervisor[i].match(Emailreg)){
                    
                } else {
                    
                    supTempArray[s] = arraySupervisor[i];
                    spvNoEmail = 1;
                    
                }
                for(var j=incr;j< req; j++){
                    if(arraySupervisor[i] == arraySupervisor[j]){
                        
                        spvSame = 1;
                    }   
                }
                //req--;
            }
            noEMail2 = supTempArray[0]; 
        } else {
            emptySpv = true;
        }
        if(emptySpv == true || spvNoEmail == 1 || spvSame == 1){
            summary++;
        }
        var emptyMntr = false;
        if(monitoring != ""){
            monitoring = monitoring.replace(/;+/g,';');
            var Lmon = monitoring[monitoring.length-1];
            if(Lmon == ";"){
                var lengMon = monitoring.length-1;
                monitoring = monitoring.substr(0,lengMon);
                //alert(monitoring);
            }
            var arrayMonitoring = monitoring.split(separator);
            var req = arrayMonitoring.length;
            var incr = 0;
            var mntrNoEmail = 0;
            var m = 0;
            var noEMail3;
            var mntrSame = 0;
            var monTempArray = new Array();
            
            for(var i= 0;i< req; i++){
                incr++;
                if(arrayMonitoring[i].match(Emailreg)){
                    //alert(arrayMonitoring[i]);
                } else {
                    //m++;
                    monTempArray[m] = arrayMonitoring[i];
                    //alert(" Not Email Address " + arrayMonitoring[i] );
                    mntrNoEmail = 1;
                }
                for(var j=incr;j< req; j++){
                    if(arrayMonitoring[i] == arrayMonitoring[j]){
                        mntrSame = 1;
                    }   
                }
                //req--;
            }
            noEMail3 = monTempArray[0];
        }
        if(mntrNoEmail == 1 || mntrSame == 1){
            summary++;
        }
        if(summary != 0){
            //alert("test val");
            if(reqNoEmail == 1){
                $('#accessTab tbody tr#s0').before('<tr id="error0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + noEMail +" Not Email Address"+'</span></td></tr>');
                document.getElementsByTagName("label")[3].style.color = "red";
                $("#validatereq").val("0");
            }
            if(reqSame == 1){
                if($("#validatereq").val() == "0"){
                    //alert("double valid"); 
                    $('#accessTab tbody tr#s0').after('<tr id="errorSame0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        +" Request Email cannot be same"+'</span></td></tr>');   
                } else {
                    $('#accessTab tbody tr#s0').before('<tr id="errorSame0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        +" Request Email cannot be same"+'</span></td></tr>');  
                }
                document.getElementsByTagName("label")[3].style.color = "red";
                $("#validatereq").val("1");
            }
            if(emptyReq == true){
                $('#accessTab tbody tr#s0').before('<tr id="error0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + " Request Email cannot be Empty"+'</span></td></tr>');
                document.getElementsByTagName("label")[3].style.color = "red";
                $("#validatereq").val("0");
            }
            if(spvNoEmail == 1){
                $('#accessTab tbody tr#s1').before('<tr id="error1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + noEMail2 +" Not Email Address"+'</span></td></tr>');
                document.getElementsByTagName("label")[4].style.color = "red";
                $("#validatesup").val("1");
            }
            if(spvSame == 1){
                if($("#validatesup").val() == "0") {
                    $('#accessTab tbody tr#s1').after('<tr id="errorSame1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    +" Supervisor Email cannot be same"+'</span></td></tr>');
                } else {
                    $('#accessTab tbody tr#s1').before('<tr id="errorSame1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    +" Supervisor Email cannot be same"+'</span></td></tr>');
                }             
                document.getElementsByTagName("label")[4].style.color = "red";
                $("#validatesup").val("0");
            }
            if(emptySpv == true){
                $('#accessTab tbody tr#s1').before('<tr id="error1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + " Supervisor Email cannot be Empty"+'</span></td></tr>');
                document.getElementsByTagName("label")[4].style.color = "red";
                $("#validatesup").val("1");
            }
            if(mntrNoEmail == 1){
                $('#accessTab tbody tr#s2').before('<tr id="error2"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + noEMail3 +" Not Email Address"+'</span></td></tr>');
                document.getElementsByTagName("label")[5].style.color = "red";
                $("#validatemon").val("0");
            }
            if(mntrSame == 1){
                $('#accessTab tbody tr#s2').before('<tr id="errorSame2"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    +" Monitor Email cannot be same"+'</span></td></tr>');
                document.getElementsByTagName("label")[5].style.color = "red";
                $("#validatemon").val("1");
            }
            return false;  
        } else {
            $("#validatereq").val("");
            $("#validatesup").val("");
            $("#validatemon").val("");
            var closed = "";
            var monspvSame = 0;
            var reqspvSame = 0;
            var reqmonSame = 0;
            
            var reqTempArray = new Array();
            var supTempArray = new Array();
            var monTempArray = new Array();
            var ori_req =0;
            var ori_sup =0;
            var ori_mon =0;
            
            if(requester != "" && supervisor != ""){
                for (var i=0; i < arrayRequest.length; i++) {
                    for(var j=0; j< arraySupervisor.length; j++) {
                        if(monitoring != ""){
                            for (var k=0; k< arrayMonitoring.length; k++) {
                                if(arrayMonitoring[k] == arraySupervisor[j])
                                {
                                    monspvSame = 1;
                                    //monTempArray[k] = arrayMonitoring[k];
                                    if(ori_sup == 0){
                                        supTempArray[ori_sup] = arraySupervisor[j];
                                    } else {
                                        if(supTempArray[ori_sup] != arraySupervisor[j]){
                                        supTempArray[ori_sup] = arraySupervisor[j];
                                        ori_sup++;
                                        }
                                    }
                                    //alert("Monitoring Email " +arrayMonitoring[k] +
                                    //    " and Supervisor Email "+arraySupervisor[j] + " cannot be same");
                                    //return false;
                                }
                                if(arrayMonitoring[k] == arrayRequest[i])
                                {
                                    reqmonSame = 1;
                                    //reqTempArray[i] = arrayRequest[i];
                                    if(ori_mon == 0){
                                        monTempArray[ori_sup] = arrayRequest[i];
                                    } else {
                                        if(monTempArray[ori_mon] != arrayRequest[i]){
                                        monTempArray[ori_mon] = arrayRequest[i];
                                        ori_mon++;
                                        }
                                    }
                                    
                                    //alert("Requester Email " +arrayRequest[i] +
                                    //    " and Monitoring Email "+arrayMonitoring[k] + " cannot be same");
                                    //return false;
                                }   
                            }
                        }
                        if(arrayRequest[i] == arraySupervisor[j])
                        {
                            reqspvSame = 1;
                            if(ori_req == 0){
                                reqTempArray[ori_req] = arraySupervisor[j];
                            } else {
                                if(reqTempArray[ori_req] != arraySupervisor[j]){
                                reqTempArray[ori_req] = arraySupervisor[j];
                                ori_req++;
                                }
                            }
                            //supTempArray[j] = arraySupervisor[j];
                            ////alert("Requester Email " +arrayRequest[i] +
                            //    " and Supervisor "+arraySupervisor[j] + " cannot be same");
                            //return false;
                        }
                    }
                }
                if(monspvSame == 1){
                    $('#accessTab tbody tr#s1').after('<tr id="errorprime1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        + JoinAll(supTempArray) +" Listed on Monitoring (Monitoring and Supervisor cannot be same)"+'</span></td></tr>');
                    document.getElementsByTagName("label")[4].style.color = "red";
                    $("#validatesup").val("2");
                
                    $('#accessTab tbody tr#s2').after('<tr id="errorprime2"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        + JoinAll(supTempArray) +" Listed on Supervisor (Monitoring and Supervisor cannot be same)"+'</span></td></tr>');
                    document.getElementsByTagName("label")[5].style.color = "red";
                    $("#validatemon").val("2");
                }
                if(reqmonSame == 1){    
                    $('#accessTab tbody tr#s0').after('<tr id="errorprime0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        + JoinAll(monTempArray) +" Listed on Monitoring (Monitoring and Requester cannot be same)"+'</span></td></tr>');
                    document.getElementsByTagName("label")[3].style.color = "red";
                    $("#validatereq").val("2");
                
                    $('#accessTab tbody tr#s2').before('<tr id="errorcross2"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        + JoinAll(monTempArray) +" Listed on Requester (Monitoring and Requester cannot be same)"+'</span></td></tr>');
                    document.getElementsByTagName("label")[5].style.color = "red";
                    $("#validatemon").val("2");
                }
                if(reqspvSame == 1){    
                    $('#accessTab tbody tr#s0').before('<tr id="errorcross0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        + JoinAll(reqTempArray) +" Listed on Supervisor (Requester and Supervisor cannot be same)"+'</span></td></tr>');
                    document.getElementsByTagName("label")[3].style.color = "red";
                    $("#validatereq").val("2");
                
                    $('#accessTab tbody tr#s1').before('<tr id="errorcross1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        + JoinAll(reqTempArray) +" Listed on Requester (Requester and Supervisor cannot be same)"+'</span></td></tr>');
                    document.getElementsByTagName("label")[4].style.color = "red";
                    $("#validatesup").val("2");
                }
            }
            if(monspvSame == 1 || reqmonSame == 1 || reqspvSame == 1){
                summary++;
            } else {
                $("#validate").val("");
                document.getElementsByTagName("label")[3].style.color = "black";
                return true;
            }
            
        }
        //alert("Monitoring Email Validation complete");
        //alert(arrayRequest+ " " + arraySupervisor +" "+ arrayMonitoring);
        //alert("Email validation COmplete");
        return false;
    }
    function load_options(id){
        $("#formTipe_tipeDummy").val(id);
        alert($("#formTipe_tipeDummy").val()); 
        alert("Var = " + $("#getListProfile").attr("var"));
        alert("Action = " + $("#getListProfile").attr("action"));
        alert("href = " + $("#getListProfile").attr("href"));
    }
    $(function() {
        if ($("#actionError").length==0 && $("#actionMessage").length==0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
        } else {
            if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(false);
            } else {
              $("#rbBS").data("rdb").callCurrent();  
            }
        }
        /*if ($("#actionError").length==0 && $("#actionMessage").length==0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
           } else if(!$("#actionMessage").length==0){
                   $("#rbBS").data("rdb").clear(true);
        } else {
            if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(true);
                $("#rdbSInquiry").click();
            } else {
                $("#rbBS").data("rdb").callCurrent();
                removesRow2("error0");
                removesRow2("errorSame0");
                removesRow2("error1");
                removesRow2("errorSame1");
                removesRow2("error2");
                removesRow2("errorSame2");
            }
        }*/
        
        $("#gRPID").change(function(e){
            $("#gRPIDname").val($(this).val());
            
            $("#requester").attr("value", null);
            $("#supervisor").attr("value", null);
            $("#monitoring").attr("value", null);
            
           
        });
        $("#schedImport").change(function(e){
            
            var schedImport = $(this).val(); 
            if ($("#requester").val()== "") {
                repParams = {};
                repParams.onClose = function(requester){
                    
                    if(requester == ""){
                        $("#requester").val("");
                        e.preventDefault();
                    } else {
                        
                        $("#requester").val(requester);
                    }  
                }
                $("#requester").focus();
                $("#formRequester_schedImport").val(schedImport);
                $("#formRequester_gRPID").val($("#gRPID").val());
                
                $("#tempRequester").click();
            } else {}
            
            supParams = {};
            supParams.onClose = function(supervisor){
                if(supervisor == ""){
                    $("#supervisor").val("");
                    e.preventDefault();
                } else {
                    $("#supervisor").val(supervisor);
                }  
            }
            $("#formSupervisor_schedImport").val(schedImport);
            $("#formSupervisor_gRPID").val($("#gRPID").val());
            
            $("#tempSupervisor").click();
            
            monParams = {};
            monParams.onClose = function(monitoring){
                if(monitoring == ""){
                    $("#monitoring").val("");
                    e.preventDefault();
                } else {
                    $("#monitoring").val(monitoring);
                }  
            }
            $("#formMonitoring_schedImport").val(schedImport);
            $("#formMonitoring_gRPID").val($("#gRPID").val());
            
            $("#tempMonitoring").click();
        });
        
        $("#btnSave").subscribe("beforeSubmit", function(event) {
            //alert("CLICK2");
            $("#frmMain").unbind("submit");
            event.originalEvent.options.submit = false;
            var result = false;
            
            var requester = $("#requester").val();
            var supervisor = $("#supervisor").val();
            var monitoring = $("#monitoring").val();
            var separator = ";";
           
            if($("#validatereq").val() != ""){
                if(document.getElementById("error0")){                    
                    removesRow2("error0");   
                } 
                if(document.getElementById("errorSame0")){
                    removesRow2("errorSame0");  
                }
                if(document.getElementById("errorcross0")){
                    removesRow2("errorcross0");  
                }
                if(document.getElementById("errorprime0")){
                    removesRow2("errorprime0");  
                }
                if(document.getElementById("error0") && document.getElementById("errorSame0")){
                    removesRow2("error0");
                    removesRow2("errorSame0"); 
                }
                if(document.getElementById("error0") && document.getElementById("errorSame0") && document.getElementById("errorcross0")){
                    removesRow2("error0");
                    removesRow2("errorSame0"); 
                    removesRow2("errorcross0"); 
                }
                if(document.getElementById("error0") && document.getElementById("errorSame0") && document.getElementById("errorcross0") && document.getElementById("errorprime0")){
                    removesRow2("error0");
                    removesRow2("errorSame0"); 
                    removesRow2("errorcross0");
                    removesRow2("errorprime0");
                }
                document.getElementsByTagName("label")[3].style.color = "black";
            }
            if($("#validatesup").val() != ""){
                if(document.getElementById("error1")){
                    
                    removesRow2("error1");   
                }
                if(document.getElementById("errorSame1")){
                    removesRow2("errorSame1");  
                } 
                if(document.getElementById("errorcross1")){
                    removesRow2("errorcross1");  
                }
                if(document.getElementById("errorprime1")){
                    removesRow2("errorprime1");  
                }
                if(document.getElementById("error1") && document.getElementById("errorSame1")){
                    removesRow2("error1");
                    removesRow2("errorSame1"); 
                }
                if(document.getElementById("error1") && document.getElementById("errorSame1") && document.getElementById("errorcross1")){
                    removesRow2("error1");
                    removesRow2("errorSame1"); 
                    removesRow2("errorcross1"); 
                }
                if(document.getElementById("error1") && document.getElementById("errorSame1") && document.getElementById("errorcross1") && document.getElementById("errorprime1")){
                    removesRow2("error1");
                    removesRow2("errorSame1"); 
                    removesRow2("errorcross1");
                    removesRow2("errorprime1");
                }
                document.getElementsByTagName("label")[4].style.color = "black";
            }
            if($("#validatemon").val() != ""){
                if(document.getElementById("error2")){
                    
                    removesRow2("error2");   
                } 
                if(document.getElementById("errorSame2")){
                    removesRow2("errorSame2");  
                }
                if(document.getElementById("errorcross2")){
                    removesRow2("errorcross2");  
                }
                if(document.getElementById("errorprime2")){
                    removesRow2("errorprime2");  
                }
                if(document.getElementById("error2") && document.getElementById("errorSame2")){
                    removesRow2("error0");
                    removesRow2("errorSame2"); 
                }
                if(document.getElementById("error2") && document.getElementById("errorSame2") && document.getElementById("errorcross2")){
                    removesRow2("error2");
                    removesRow2("errorSame2"); 
                    removesRow2("errorcross2"); 
                }
                if(document.getElementById("error2") && document.getElementById("errorSame2") && document.getElementById("errorcross2") && document.getElementById("errorprime2")){
                    removesRow2("error2");
                    removesRow2("errorSame2"); 
                    removesRow2("errorcross2");
                    removesRow2("errorprime2");
                }
                document.getElementsByTagName("label")[5].style.color = "black";
            }   
             
            result = SeparatorRequest(requester,supervisor,monitoring,separator);
    
            if(result == true){
                if (validateForm_frmMain()) {
                    dlgParams = {};
                    dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                    dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                    dlgParams.event = event;
                    dlgParams.onSubmit = function() {
                        dlgParams.event.originalEvent.options.submit = true;
                        $("#btnSave").unsubscribe("beforeSubmit");
                        //alert("CLICK");
                        $("#btnSave").click();
                        $("#btnSave").button("disable");
                    };
                    $("#ph-dlg").dialog("option", "position", "center");
                    //$("#ph-dlg").dialog("option", "width", $("body").width()*2/4);
                    //$("#ph-dlg").dialog("option", "height", $("body").height()*2/4);
                    $("#ph-dlg").dialog("option", "width", "320");
                    $("#ph-dlg").dialog("option", "height", "180");
                    $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function() {
                        $("#tempDlgAuth").click();
                    })
                    .dialog("open");
                }
            }
        });
    });
</script>
<% }%>
