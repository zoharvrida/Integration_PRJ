<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:if test="%{#session.idMenu.equals('31301')}">
	<s:if test="%{#request.resultType.equals('cutoff')}">
		Already cut off time
		
		<s:url var="ajaxUpdateTitle" action="31301_title_" />
		<s:url var="ajaxUpdateButtons" action="31301_blank" />
		<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
		<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
		
		<script type="text/javascript">
			$("#rbBS").buttonset("destroy");
			$("#tempTitle").click();
			$("#tempButtons").click();
		</script>
	</s:if>
	
	<s:if test="%{#request.resultType.equals('balance')}">
		<script type="text/javascript">
			$(function() {
				if (tempParam.onClose != undefined) {
					balance = '<s:property value="%{balance}"  />';
					<s:if test="%{((svd!=null) && (svd.trim()!=''))}">
						svd = <s:property value="%{contentData}" escapeHtml="false" />;
					</s:if>
					<s:else>
						svd = '';
					</s:else>
					sessionFlag = '<s:property value="%{#session.SKHT}" />';
					tempParam.onClose(balance, sessionFlag, svd);
				}
			});
		</script>
	</s:if>
	
	<s:if test="%{#request.resultType.equals('printAwal')}">
		<script type="text/javascript">
			var printPDFFunction = function() {
				if (tempParams.onClose != undefined) {
					var skhtPrintDeposit = <s:property value="%{contentData}" escapeHtml="false" />;
					sessionFlag = '<s:property value="%{#session.SKHT}" />';
					tempParams.onClose(skhtPrintDeposit, sessionFlag, '<s:property value="%{state}" />');
				}
			}
			
			$(function() {
				var messageType = '<s:property value="%{#request.SKHT_MESSAGE_TYPE}" />';
				
				<s:if test="%{#request.SKHT_MESSAGE.equals('') == false}">
					messageBoxClass(parseInt(messageType), "divSkhtAwalMessage", '<s:property value="%{#request.SKHT_MESSAGE}" />', printPDFFunction);
				</s:if>
				<s:else>
					printPDFFunction();
				</s:else>
			});
		</script>
	</s:if>
</s:if>

