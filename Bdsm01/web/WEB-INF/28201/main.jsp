<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="28201_title_" />
	<s:url var="ajaxUpdateButtons" action="28201_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>


	<s:form id="formValidateAcc" action="20201_validateAcc.action">
		<s:hidden name="noAccount" />
		<s:token name="accToken" />
	</s:form>
	<sj:a id="tempValidateAcc" formIds="formValidateAcc" targets="ph-temp" cssClass="ui-helper-hidden" onSuccessTopics="postValAcc"></sj:a>

	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />


	<s:form id="frmMain" name="frmMain" action="20201" focusElement="frmMain_noAccount">
		<s:textfield name="noAccount" requiredLabel="true" size="16" maxlength="16" cssClass="ui-widget ui-widget-content" key="label.noAccount" />
		<s:hidden name="flgAcct" />
		<sj:submit id="btnFind" formIds="frmMain" buttonIcon="ui-icon-gear" button="true" key="button.find" disabled="false" 
				targets="ph-main" onBeforeTopics="beforeSubmit" />
		<s:token name="sumToken"/>
	</s:form>


	<%-- <s:url var="urlTab0" action="20201T0_none"/> --%>
	<s:url var="urlTab1" action="20201T1_none" />
	<s:url var="urlTab2" action="20201T2_none" />
	<sj:tabbedpanel id="tabbedpanel" disabled="true">
		<%-- <sj:tab id="t0" href="%{urlTab0}" label="Limit Monthly"/> --%>
		<sj:tab id="t1" href="%{urlTab1}" label="Limit UD" />
		<sj:tab id="t2" href="%{urlTab2}" label="Pending Transactions" />
	</sj:tabbedpanel>


	<script type="text/javascript">
		$(function() {
			var myFunction = function pad(str, max) {
				return str.length < max ? pad("0" + str, max) : str;
			};

			$("#frmMain_noAccount").die('keydown');
			$("#frmMain_noAccount").live('keydown', function(e) {
				if (e.keyCode == 9) {
					var noAccount = $.trim($("#frmMain_noAccount").val());
					if (noAccount.indexOf('.') != -1) {
						while (noAccount.indexOf('.') != -1) {
							noAccount = noAccount.replace(".", "");
						}
						noAccount = myFunction(noAccount, 12);
						$("#frmMain_noAccount").val(noAccount);
					}
				}
			});

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

			$("#tabbedpanel")
				.tabs({
					select : function(event, ui) {
						var selected = ui.index;
						switch (selected) {
							/* case 0: 
							     $('#tabbedpanel').tabs('url', selected, '20201T0_input?flgAcct=' + $("#frmMain_flgAcct").val() + 
							     '&noAccount=' + $.trim($("#frmMain_noAccount").val()) + '&period=' + $("#frmMain_period").val());
							     break;*/
							case 0:
								$('#tabbedpanel').tabs('url', selected, '28201T1_input?flgAcct=' + $("#frmMain_flgAcct").val() 
									+ '&noAccount=' + $("#frmMain_noAccount").val());
								break;
							case 1:
								$('#tabbedpanel').tabs('url', selected, '28201T2_input?noAccount=' + $.trim($("#frmMain_noAccount").val()));
								break;
							default:
								break;
						}
					}
				});

			$("#btnFind").subscribe("beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;

				var noAcct = $.trim($("#frmMain_noAccount").val());
				if (noAcct == '') {
					alert('Account No cannot be empty');
					$("#frmMain_noAccount").focus();
					event.preventDefault();
					return;
				}

				tempParams = {};
				tempParams.flgAcct = "frmMain_flgAcct";
				tempParams.noAccount = "frmMain_noAccount";
				$("#formValidateAcc_noAccount").val($("#frmMain_noAccount").val());
				$("#tempValidateAcc").click();
			});

			$("#tempValidateAcc").unsubscribe("postValAcc");
			$("#tempValidateAcc").subscribe("postValAcc", function(event, data) {
				if ($("#frmMain_flgAcct").val() == '0' || $("#frmMain_flgAcct").val() == '1') {
					var noAcct = $.trim($("#frmMain_noAccount").val());
					var flgAcct = $("#frmMain_flgAcct").val();
					var selected = $('#tabbedpanel').tabs('option', 'selected');

					if (selected == 0) {
						$('#tabbedpanel').tabs('url', selected, '28201T1_input?flgAcct=' + flgAcct + '&noAccount=' + noAcct);
						$('#tabbedpanel').tabs('load', selected);
						$("#tabbedpanel").tabs("option", "disabled", []);
						$("#frmMain_noAccount").attr("readonly", "true");
						$("#btnFind").button("disable");
					}

					if (flgAcct == '0') {
						$("#tabbedpanel").tabs("option", "disabled", [2]);
					}
				}
			});
		});
	</script>
</s:if>