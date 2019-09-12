<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:if test="%{#session.idMenu.equals('31302')}">
	<s:if test="%{#request.resultType.equals('cutoff')}">
		Already cut off time
		
		<s:url var="ajaxUpdateTitle" action="31302_title_" />
		<s:url var="ajaxUpdateButtons" action="31302_blank" />
		<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
		<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
		
		<script type="text/javascript">
			$("#rbBS").buttonset("destroy");
			$("#tempTitle").click();
			$("#tempButtons").click();
		</script>
	</s:if>
	
	<s:if test="%{#request.resultType.equals('acctNo')}">
		<script type="text/javascript">
			$(function() {
				if (inqParam.onClose != undefined) {
					<s:if test="%{(svdp!=null)}">
						svdp = <s:property value="%{contentData}" escapeHtml="false" />;
					</s:if>
					<s:else>
						svdp = '';
					</s:else>
					
					var strData = '';
					<s:if test="%{(strData.get('strData')!=null)}">
						strData = <s:property value="%{strData.strData}" escapeHtml="false" />;
						for (var i in strData) {
							var $e = $("#frmMain_strData_" + i);
							if ($e.hasClass("cls-money"))
								$e.autoNumeric('set', strData[i]);
							else
								$e.val(strData[i]);
						}
					</s:if>
					
					sessionFlag = '<s:property value="%{#session.SKHT}" />';
					var message = '<s:property value="%{#request.SKHT_MESSAGE}" />';
					inqParam.onClose(svdp, sessionFlag, message);
				}
			});
		</script>
	</s:if>
	
	<s:if test="%{#request.resultType.equals('balance')}">
		<script type="text/javascript">
			$(function() {
				if (tempParam.onClose != undefined) {
					balance = '<s:property value="%{balance}" escapeHtml="false" />';
					sessionFlag = '<s:property value="%{#session.SKHT}" />';
					$("#frmMain_state").val("<s:property value='%{state}' />");
					
					tempParam.onClose(balance, sessionFlag);
				}
			});
		</script>
	</s:if>
	
	<s:if test="%{#request.resultType.equals('printAkhir')}">
		<script type="text/javascript">
			var printPDFFunction = function() {
				if (tempParams.onClose != undefined) {
					skpPrint = <s:property value="%{contentData}" escapeHtml="false" />;
					sessionFlag = '<s:property value="%{#session.SKHT}" />';
					
					var strData = {};
					<s:if test="%{(strData.get('strData')!=null)}">
						strData = <s:property value="%{strData.strData}" escapeHtml="false" />;
					</s:if>
					tempParams.onClose(skpPrint, strData, sessionFlag, '<s:property value="%{state}" />');
				}
			}
			
			$(function() {
				var messageType = '<s:property value="%{#request.SKHT_MESSAGE_TYPE}" />';
				
				<s:if test="%{#request.SKHT_MESSAGE.equals('') == false}">
					messageBoxClass(parseInt(messageType), "divSkhtAkhirMess", '<s:property value="%{#request.SKHT_MESSAGE}" />', printPDFFunction);
				</s:if>
				<s:else>
					printPDFFunction();
				</s:else>
			});
		</script>
	</s:if>
</s:if>

