<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:url var="ajaxUpdateTitle" action="26301_title" />
    <s:url var="ajaxUpdateButtons" action="26301_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
    <script type="text/javascript" src="_js/jquery.maskedinput-1.2.2.js"></script>

    <s:form id="frmMain" name="frmMain" action="26301" theme="css_xhtml" method="post" validate="true">
         <script type="text/javascript">
		<%@include file="formValidation.js" %>
	</script>
	 <table class="wwFormTable">
            <tbody>
                <tr>

                    <td class="tdLabel" style="font-style:italic"><s:text name="label.customer.name" /></td>
                    <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                    <td style="padding-right: 5px">
		<s:textfield 
                name="customerName" 
                size="100"
                maxlength="90" 
                cssClass="ui-widget ui-widget-content"
                cssStyle="text-transform:uppercase"
                onblur="javascript:this.value=this.value.toUpperCase();"
                />
                    </td>
					</tr>
					<tr>
                    <td class="tdLabel" style="font-style:italic"><s:text name="label.dateof.birth" /></td>
                    <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                    <td style="padding-right: 5px">
            <sj:datepicker
                name="dateofBirth"
                id="dateofBirth"
                displayFormat="dd/mm/yy"
                firstDay="1"
                yearRange="-120:-10"
                buttonImageOnly="true"
                showOn="both"
				cssClass="ui-widget ui-widget-content" 
                changeMonth="true" 
                changeYear="true"
                numberOfMonths="1" />   
					</tr>
					<tr>
                    <td class="tdLabel" style="font-style:italic"><s:text name="label.nokartu.iden" /></td>
                    <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                    <td style="padding-right: 5px">
        <s:textfield 
            name="noKartuIden" 
            size="20"
            maxlength="16" 
            cssClass="ui-widget ui-widget-content"
                />   
                    </td>
					</tr>
					<tr>
                    <td class="tdLabel" style="font-style:italic"><s:text name="label.no.card" /></td>
                    <td class="tdLabel" style="font-style:italic">:</td>
                    <td style="padding-right: 5px">
        		<s:textfield 
            name="noCard" 
            size="30"
            maxlength="16" 
            cssClass="ui-widget ui-widget-content"
            cssStyle="text-transform:uppercase"
            onblur="javascript:this.value=this.value.toUpperCase();"
            />
                    </td>
					
                </tr>
            </tbody>
        </table>
        <s:hidden name="so.extUser" />            
            <s:hidden name="hdFlagCard" />
            <s:hidden name="flagButton" />       
            <s:hidden name="state" value="1"/>
            <s:hidden name="inputNik" />
            <s:hidden name="so.flagCIF" />
        <s:hidden name="so.menuAccount" />
        <s:hidden name="so.applicationID" />
            <s:hidden name="skip" />
            <s:hidden name="gridClear" />
			<s:hidden name="cifNo" />
        <s:hidden name="flagsCIF" />
        <s:hidden name="so.branchCode" />
		
		<s:hidden name="recordsTable" />
        <s:hidden name="recordsTableNIK" />
            <table>
                <tbody>
                    <tr>
                        <td>
                            <sj:submit 
                                id="btnCustSearch" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.search.customer" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeSubmit" 
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
		<%-- Revision : VA testing Fixing
Change Date : 14-Januari-2015   
Changer : v00019722 
         Begin 1 --%>
            <%-- TabbedPanel for Grid --%>
            <s:url var="urlTab" action="26301Tab_none">
            </s:url>
            <s:url var="urlTabNik" action="26301TabNik_none">
            </s:url>
        <s:url var="urlTabCard" action="26301TabCard_none">
        </s:url>
		<%-- End 1 --%>
            <sj:tabbedpanel id="tabbedPanel" disabled="true">
                <sj:tab id="tabCasastatus" href="%{urlTab}" />
            </sj:tabbedpanel>
            <sj:tabbedpanel id="tabbedPanelNik" disabled="true">
                <sj:tab id="tabCasastatusnik" href="%{urlTabNik}"  />
            </sj:tabbedpanel>
        <sj:tabbedpanel id="tabbedPanelCard" disabled="true">
            <sj:tab id="tabCasastatuscard" href="%{urlTabCard}"  />
        </sj:tabbedpanel>
        <table>
            <tbody>
                <tr><td>           
            <sj:submit 
                id="btnNext" 
                formIds="frmMain" 
                buttonIcon="ui-icon-gear"
                button="true" 
                key="button.casa.next" 
                disabled="false" 
                targets="ph-main"
                onBeforeTopics="beforeNext"
                />
                    </td>
            </tbody>
        </table>
            <s:token name="SearchToken" />
        <div id="divMain26301" />
    </s:form>


    <script type="text/javascript">
        jQuery(document).ready(function() {
            $('#tabbedPanel').hide();
            $('#tabbedPanelNik').hide();
            $('#tabbedPanelCard').hide();
            $('#tabbedPanel ul').attr('style', 'display:none');
            $('#tabbedPanelNik ul').attr('style', 'display:none');
            $('#tabbedPanelCard ul').attr('style', 'display:none');
			$("#divwaitingMessage").remove();
			$("#divwaitingRespond").remove();
            //$("#divMessage").remove();
            $("#divMessage").dialog("close");
            $('#dateofBirth').mask("99/99/9999",{placeholder:"dd/mm/yyyy"}); 
            $('#frmMain_noKartuIden').mask("9999999999999999"); 
            //

            var menuAction = $(".jstree-clicked").text();
            //alert($("#ph-title").html());
            if(menuAction!=''){
                var result = menuAction.split("-");
                $("#frmMain_so_menuAccount").val(result[0].substr(1,result[0].length).toString());
            } 
			//$("#rbBS").parent().append($("#spinCon").detach());
            $('#tabbedPanel ul').hide();
            $('#tabbedPanelNik ul').hide();
            $('#tabbedPanelCard ul').hide();

            $('#btnNext').hide();

            if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
                $("#rbBS").buttonset("destroy");
                $("#tempTitle").click();
                $("#tempButtons").click();
            } else {
                $("#rbBS").data("rdb").callCurrent();
                if ($("#actionError").length == 0) {
                    $("#rbBS").data("rdb").clear(false);
                }
            }

            $("#frmMain_customerName").die('keydown');
            $("#frmMain_dateofBirth").die('keydown');
            $("#frmMain_noKartuIden").die('keydown');
            $("#frmMain_noCard").die('keydown');


            $("#frmMain_customerName").live('keydown', function(e) {
                if (e.ctrlKey)
                    e.preventDefault();
            });
            $("#frmMain_dateofBirth").live('keydown', function(e) {
                if (e.ctrlKey)
                    e.preventDefault();
            });
            $("#frmMain_noKartuIden").live('keydown', function(e) {
                if (e.ctrlKey)
                    e.preventDefault();
            });
            $("#frmMain_noCard").live('keydown', function(e) {
                if (e.ctrlKey)
                    e.preventDefault();
            });

			$("#btnCustSearch").unsubscribe("beforeSubmit");
            $("#btnCustSearch").subscribe("beforeSubmit", function(event) {
                $("#frmMain_recordsTable").val(null);
				$("#frmMain_recordsTableNIK").val(null);
                event.originalEvent.options.submit = false;
               // var customerName = $.trim($("#frmMain_customerName").val());
               // var dateofBirth = $.trim($("#dateofBirth").val());
                //var noKartuIden = $.trim($("#frmMain_noKartuIden").val());
                var noCard = $.trim($("#frmMain_noCard").val());
                var flagging = "0";
                if (noCard != '') {
                   if(validateForm_frmInquiryCard26301()){
                        flagging = "0";
                    } else {
                        flagging = "1";
                    }      
                    } else {
                    if(validateForm_frmInquiry26301()){
                        flagging = "0";
                } else {
                        flagging = "1";
                    }  
                }

                if (flagging == "1"){
                    var flags = "0";
                    $("#frmMain_flagButton").val(flags.toString());
                    return;
                }
                else {
                    var noCard = $.trim($("#frmMain_noCard").val());
					//alert(3);
                    if (noCard != '') {
						//alert(4);
                        $('#tabbedPanelCard').tabs('url', 0, '26301TabCard_input');
                        $('#tabbedPanelCard').tabs({
                            ajaxOptions: {
                                type: 'POST',
                                data: {
                                    'noCard': $("#frmMain_noCard").val(),
                                    'flagCard': $("#frmMain_hdFlagCard").val(),
                                    'flagButton': $("#frmMain_flagButton").val()

                                }
                            }
                        });
                        $('#tabbedPanelCard').tabs('load', 0);
                        $('#tabbedPanelCard').show();
						$('#tabbedPanel').hide();
						$('#tabbedPanelNik').hide();
                    } else if(noCard == '') {
						//alert('4b');
                    $('#tabbedPanel').tabs('url', 0, '26301Tab_input');
                    $('#tabbedPanelNik').tabs('url', 0, '26301TabNik_input');

                    $('#tabbedPanel').tabs({
                        ajaxOptions: {
                            type: 'POST',
                            data: {
                                'customerName': $("#frmMain_customerName").val(),
                                'dateofBirth': $("#dateofBirth").val(),
                                'flagCard': $("#frmMain_hdFlagCard").val(),
                                    'flagButton': $("#frmMain_flagButton").val(),
                                    'noKartuIden': $("#frmMain_noKartuIden").val()
                            }
                        }
                    });
                    $('#tabbedPanelNik').tabs({
                        ajaxOptions: {
                            type: 'POST',
                            data: {
                                'noKartuIden': $("#frmMain_noKartuIden").val(),
                                'flagCard': $("#frmMain_hdFlagCard").val(),
                                'flagButton': $("#frmMain_flagButton").val()
                            }
                        }
                    });
                    $('#tabbedPanel').tabs('load', 0);
                    $('#tabbedPanelNik').tabs('load', 0);
                    $('#tabbedPanel').show();
                    $('#tabbedPanelNik').show();
                        $('#tabbedPanelCard').hide();
                }
                    //$('#btnNext').show();
                }
            });
            var newCIF = false;
            $("#frmMain_so_flagCIF").val(newCIF);

            $("#btnNext").subscribe("beforeNext", function(event) {
                $("#frmMain").unbind("submit");
                event.originalEvent.options.submit = false; 
				/* Revision : Validation Date Of Birth
Change Date : 14-Januari-2015   
Changer : v00019722 
         Begin 1 */
                result = true;
                var menuActionGo = $("#ph-title").text();
                var menuTemp = $("#frmMain_so_menuAccount").val();
                var datBirth = $.trim($("#dateofBirth").val());
				var cardNo= $.trim($("#frmMain_noCard").val());
                var datehid = "<s:date name="%{#session.dtBusiness}" format="d/MM/yyyy"  />";
                var branchCode = "<s:property value="%{#session.cdBranch}" />";
                if (datBirth != '') {
                    if (validateDate(datBirth, datehid.toString(), 10)) {
					//alert("Click Button Next - 1");
                    } else {
						if(cardNo != ''){
							result= true;
                    } else {
                        result = false;
						//alert("Click Button Next - 2");
						}
                    }
                }
                if(menuTemp == '') {
                    var resultMenu = menuActionGo.split("-");
                    $("#frmMain_so_menuAccount").val(resultMenu[0].toString());
                }
                var flags = "1";

				//alert(result);
/* End 1 */
                flagAuth = "N";
                var messaging = "Please Wait Your Request Being Processed..";
                if(result == true){
                    if ($('#frmMain_noKartuIden').val() != "") {
						$('#frmMain_inputNik').val($('#frmMain_noKartuIden').val());
                    }else{
						$('#frmMain_inputNik').val($('#frmMain_noCard').val());
                    }
					//event.originalEvent.options.submit = true; 
                    $("#frmMain_so_branchCode").val(branchCode);
                    $("#frmMain_flagButton").val(flags.toString());
                    //$("#frmMain_so_flagCIF").val(newCIF);
                    $("#btnNext").unsubscribe("beforeNext").click();
                    $("#spinner").removeClass("ui-helper-hidden");
                    waitingMessage(3,messaging,"divMain26301");
                }
            });
        });
    </script>
</s:if>
