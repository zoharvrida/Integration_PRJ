<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="22201_title" />
	<s:url var="ajaxUpdateButtons" action="22201_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />

	<s:form id="frmFindCardNo" action="22201_validateCardNo.action">
		<s:hidden name="cardNo" />
		<s:token />
	</s:form>
	<sj:a id="aFindCardNo" formIds="frmFindCardNo" targets="ph-temp" cssClass="ui-helper-hidden" onSuccessTopics="postValidateCardNo" />

	<s:form id="frmMain" name="frmMain" action="22201" focusElement="frmMain_cardNo">
		<s:textfield 
			name="cardNo" 
			requiredLabel="true" 
			size="20"
			maxlength="16" 
			cssClass="ui-widget ui-widget-content"
			key="label.card.no" 
		/>
		<s:hidden name="hdFlagCard" />
		<sj:submit 
			id="btnFind" 
			formIds="frmMain" 
			buttonIcon="ui-icon-gear"
			button="true" 
			key="button.find" 
			disabled="false" 
			targets="ph-main"
			onBeforeTopics="beforeSubmit" 
		/>
		<s:token />
	</s:form>
	
	<%-- TabbedPanel for Grid --%>
	<s:url var="urlTab" action="22201Tab_none">
		<param name="cardNo" value="" />
	</s:url>
	<sj:tabbedpanel id="tabbedPanel" disabled="true">
		<sj:tab id="tabTPINStatus" href="%{urlTab}" label="TPIN Status" />
	</sj:tabbedpanel>
	

	<script type="text/javascript">
		jQuery(document).ready(function() {
			$('#tabbedPanel').hide();
			$('#tabbedPanel ul').attr('style', 'display:none');
			
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
			
			$("#frmMain_cardNo").bind("contextmenu", function(e) {
				e.preventDefault();
			});
			
			$("#frmMain_cardNo").die('keydown');
			$("#frmMain_cardNo").live('keydown', function(e) {
				if (e.ctrlKey)
					e.preventDefault();
			});
			
			$("#frmMain_cardNo").die('keypress');
			$("#frmMain_cardNo").live('keypress', function(e) {
				var key_ = e.which;
				var char_ = String.fromCharCode(key_);
				
				if (((char_ >=  "0") && (char_ <= "9")) || // numbers
					("8|37|39|46".indexOf(key_) > -1) || // backspace, delete, left, right
					((e.shiftKey) && (key_==45))) // Shift + Insert
					;
				else
					e.preventDefault();
			});
			
			$("#btnFind").subscribe("beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				var cardNo = $.trim($("#frmMain_cardNo").val());
				
				if (cardNo == '') {
					alert("Field 'Card No' cannot be empty");
					$("#frmMain_cardNo").focus();
					
					return;
				}
				else {
					tmpObj = {};
					tmpObj.hdFlagCard = "frmMain_hdFlagCard";
					tmpObj.cardNo = "frmMain_cardNo";
					
					$('#frmFindCardNo_cardNo').val($('#frmMain_cardNo').val());
					$('#aFindCardNo').click();
				}
			});
			

			$("#aFindCardNo").unsubscribe("postValidateCardNo");
			$("#aFindCardNo").subscribe("postValidateCardNo", function(event, data) {
				if ('|-2|0|'.indexOf($("#frmMain_hdFlagCard").val()) > -1) {
					$('#tabbedPanel').tabs('url', 0, '22201Tab_input');
					$('#tabbedPanel').tabs({
						ajaxOptions :{
							type: 'POST',
							data: {
								'cardNo': $("#frmMain_cardNo").val(),
								'flagCard': $("#frmMain_hdFlagCard").val()
							}
						}						
					});
					$('#tabbedPanel').tabs('load', 0);
					$('#tabbedPanel').show();
					//$("#frmMain_cardNo").attr("readonly", "true");
					//$("#btnFind").button("disable");
				}
				else {
					$('#tabbedPanel').tabs('url', 0, '22201Tab_none');
					$('#tabbedPanel').tabs('load', 0);
					$('#tabbedPanel').hide();
					$('#frmMain_cardNo').focus();
				}
			});

		});
	</script>
</s:if>
