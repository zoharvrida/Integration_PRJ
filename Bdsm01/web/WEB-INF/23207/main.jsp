<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="23207_title" />
	<s:url var="ajaxUpdateButtons" action="23207_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="23207" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<s:form id="frmMain" name="frmMain" action="23207" method="post" validate="true">
		<s:select 
			name="batchNo"
			list="mapFilename"
			emptyOption="true"
			onchange="$('#btnFind').click()"
		/>
		
		<sj:a 
			id="btnFind" 
			button="true" 
			disabled="false"
			cssStyle="display:none"
		>
			<s:text name="button.search" />
		</sj:a>
		<s:token />
	</s:form>
	<br>
	
	<%-- TabbedPanel for Grid --%>
	<s:url var="urlTab" action="23207Tab_none" />
	
	<sj:tabbedpanel id="tabbedPanelHeader" disabled="true" cssStyle="max-width:950px">
		<sj:tab id="tabHeader" href="%{urlTab}" label="SKNNG Outward Credit Header" />
	</sj:tabbedpanel>
	
	<fieldset id="fsData" class="ui-widget-content ui-corner-all ui-helper-hidden">
		<legend class="ui-widget-header ui-corner-all">Batch</legend>
		<s:form id="frmData" name="frmData" action="23207_execute" method="post" theme="css_xhtml">
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblBatchId" key="label.batchid" />
						</td>
						<td>
							<s:textfield 
								name="batchId"
								readonly="true"
								cssClass="ui-widget ui-widget-content" 
							/>
						</td>
						<td><s:textfield name="txtDummy" size="1" disabled="true" cssStyle="visibility:hidden" /></td>
						<td>
							<s:label id="lblDateTimeMessage" key="label.date.time.message" />
						</td>
						<td>
							<s:textfield
								name="dateTimeMessage"
								size="30"
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblSenderCode" key="label.sender.code" />
						</td>
						<td>
							<s:textfield
								name="senderCode" 
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
						<td>&nbsp;</td>
						<td>
							<s:label id="lblSenderCityCode" key="label.sender.city.code" />
						</td>
						<td>
							<s:textfield
								name="senderCityCode"
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<s:label id="lblBatchDate" key="label.batch.date" />
						</td>
						<td>
							<s:textfield
								name="batchDate"
								readonly="true"
								cssClass="ui-widget ui-widget-content"
							/>
						</td>
						<td>&nbsp;</td>
						<td>
							<s:label id="lblRejectCodeText" key="label.reject.code" />
						</td>
						<td>
							<s:label id="lblRejectCode" key="" />
						</td>
					</tr>
					<tr>
						<td colspan="5">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="5">
							<sj:submit
								id="btnSubmit"
								formIds="frmData"
								buttonIcon="ui-icon-gear"
								button="true"
								key="button.execute"
								targets="ph-temp"
								indicator="myIndicator"
								onBeforeTopics="onBeforeSubmit"
								onClickTopics="onClick"
								onCompleteTopics="onCompleted" 
							/>
						</td>
					</tr>
					<tr>
						<td colspan="5">&nbsp;</td>
					</tr>
				</tbody>
			</table>
			
			<s:hidden name="batchNo" />
			<s:hidden name="recordId" />
			<s:hidden name="batchNoWS" />
			<s:hidden name="idMaintainedSpv" />
			
			
			<sj:tabbedpanel id="tabbedPanelDetail" disabled="true" cssStyle="max-width:900px">
				<sj:tab id="tabDetail" href="%{urlTab}" label="SKNNG Outward Credit Detail" />
			</sj:tabbedpanel>
			
			<s:token />
		</s:form>
	</fieldset>
	
	<sj:dialog id="myIndicator" autoOpen="false" modal="true" resizable="false" width="auto">
		<img src='<s:url value="/spinner1.gif" />' />
	</sj:dialog>
	
	
	<script type="text/javascript">
		$(function() {
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("min-width", function() {
					var width_ = ((parseInt($(this).css("min-width")) + 25) + "px");
					return width_;
				})
				.css("width", function() {
					var width_ = ((parseInt($(this).css("min-width")) + 25) + "px");
					return width_;
				});
			$("#myIndicator").parent().find(".ui-dialog-titlebar").css("display", "none");
				
			
			$("#tabbedPanelHeader").hide();
			$('#tabbedPanelDetail').hide();
			//$('#tabbedPanelHeader ul').attr('style', 'display:none');
			if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				$("#rbBS").data("rdb").callCurrent();
				if ($("#actionError").length == 0) {
					$("#rbBS").data("rdb").clear(false);
				}
			}
			
			$("#btnFind").click(function() {
				$("#fsData").hide();
				
				$('#tabbedPanelHeader').tabs({
					ajaxOptions :{
						type: 'POST',
						data: {
							'batchNo': $("#frmMain_batchNo").val()
						}
					}
				});
				
				$("#tabbedPanelHeader").tabs({selected : 0});
				$("#tabbedPanelHeader").tabs("url", 0, "23207Tab_execute.action");
				$("#tabbedPanelHeader").tabs("load", 0);
				$("#tabbedPanelHeader").show();
			});
			
			
			$("#btnSubmit")
				.unsubscribe("onBeforeSubmit")
				.subscribe("onBeforeSubmit", funcOnBeforeSubmit);
			
			$("#btnSubmit")
				.unsubscribe("onClick")
				.subscribe("onClick", function() {
					if ($("#frmData_idMaintainedSpv").attr("value") != "")
						$("#myIndicator").dialog("open");
				});
			
			$("#btnSubmit")
				.unsubscribe("onCompleted")
				.subscribe("onCompleted", function() {
					$("#myIndicator").dialog("close");
				});
		});
		
		function funcOnBeforeSubmit(event) {
			event.originalEvent.options.submit = false;
				
			dlgParams = {};
			dlgParams.idMaintainedSpv = "frmData_idMaintainedSpv";
			dlgParams.onSubmit = function() {
				$("#btnSubmit")
					.unsubscribe("onBeforeSubmit")
					.click();
			};
			
			$("#ph-dlg")
				.dialog("option", "position", "center")
				.dialog("option", "width", "320")
				.dialog("option", "height", "180")
				.html("Please wait...")
				.unbind("dialogopen")
				.bind("dialogopen", function() {
					$("#aDlgAuth").click();
				})
				.dialog("open");
		}
		
	</script>
</s:if>

