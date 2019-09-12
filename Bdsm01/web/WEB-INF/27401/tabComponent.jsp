<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:form id="frmComponent" action="27401" theme="css_xhtml" validate="false">
	<fieldset id="fsCommitment" class="ui-widget-content ui-corner-all">
		<legend class="ui-widget-header ui-corner-all"><s:text name="label.mitrapasti.fieldset.legend.commitments" /></legend>
		<table id="tblCommitment">
			<thead>
				<tr>
					<td colspan="2" width="48%" style="min-width:370px" />
					<td align="center" width="13%" style="min-width:120px; visibility:hidden"><b>Minimum Value</b></td>
					<td align="center" width="13%" style="min-width:120px; visibility:hidden"><b>Maximum Value</b></td>
					<td align="center" width="13%" style="min-width:120px"><b>Base Value</b></td>
					<td align="center" style="min-width:120px"><b>Final Value</b></td>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listCompCommitment">
					<tr>
						<td colspan="2" style="vertical-align: top">
							<div class="wwgrp">
								<b><s:property value="%{component.name}" /></b>
							</div>
						</td>
						<td align="right" id='minValue<s:property value="%{component.code}" />' style="visibility:hidden">
							<s:if test='%{component.dataType == "M"}'>
								<s:text name="format.money">
									<s:param name="value" value="%{(classDetails.minimumValue == null)? 0: classDetails.minimumValue}" />
								</s:text>
							</s:if>
							<s:elseif test='%{component.dataType=="N"}'>
								<s:property value="%{(classDetails.minimumValue == null)? 0: classDetails.minimumValue}" />
							</s:elseif>
						</td>
						<td align="right" id='maxValue<s:property value="%{component.code}" />' style="visibility:hidden">
							<s:if test='%{component.dataType == "M"}'>
								<s:text name="format.money">
									<s:param name="value" value="%{(classDetails.maximumValue == null)? 0: classDetails.maximumValue}" />
								</s:text>
							</s:if>
							<s:elseif test='%{component.dataType=="N"}'>
								<s:property value="%{(classDetails.maximumValue == null)? 0: classDetails.maximumValue}" />
							</s:elseif>
						</td>
						<td align="right">
							<s:if test='%{component.dataType == "M"}'>
								<s:text name="format.money">
									<s:param name="value" value="%{(classDetails.defaultValue == null)? 0: classDetails.defaultValue}" />
								</s:text>
							</s:if>
							<s:elseif test='%{component.dataType=="N"}'>
								<s:property value="%{(classDetails.defaultValue == null)? 0: classDetails.defaultValue}" />
							</s:elseif>
						</td>
						<td align="right">
							<s:if test='%{component.dataType=="S"}'>
								<s:textarea
									name="strData.comp%{component.code}"
									requiredLabel="true"
									rows="6"
									cols="16"
									cssStyle="resize: none" 
								/>
							</s:if>
							<s:else>
								<s:textfield 
									name="strData.comp%{component.code}" 
									value="%{getText('{0, number, #.##;-#.##}', {value}).trim()}" 
									cssClass='%{(component.dataType=="M")? "compMoney": ""}' 
								/>
							</s:else>
							<s:hidden name="strData.type%{component.code}" value="%{component.dataType}" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</fieldset>
	
	<fieldset id="fsLogic" class="ui-widget-content ui-corner-all">
		<legend class="ui-widget-header ui-corner-all"><s:text name="label.mitrapasti.fieldset.legend.cashbackcomponents" /></legend>
		<table id="tblCashback">
			<thead>
				<tr>
					<td colspan="2" width="48%" style="min-width:370px" />
					<td align="center" width="13%" style="min-width:120px; visibility:hidden"><b>Minimum Value</b></td>
					<td align="center" width="13%" style="min-width:120px; visibility:hidden"><b>Maximum Value</b></td>
					<td align="center" width="13%" style="min-width:120px"><b>Base Value</b></td>
					<td align="center" style="min-width:120px"><b>Final Value</b></td>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listCompLogic">
					<tr>
						<td>
							<b><s:property value="%{logic.name}" /></b>
						</td>
						<td colspan="5">
							<s:checkbox name="chkComp%{logic.code}" value="%{isLogicChecked}" onclick="chkCompClicked(this);" cssStyle="display:none" />
							<s:hidden id="childCount%{logic.code}" value="%{parameters.size}" disabled="true" />
						</td>
					</tr>
					<s:iterator value="parameters">
						<tr
							<s:if test='%{component.type=="R"}'> style='display:none' </s:if>
						>
							<td />
							<td style="vertical-align: top">
								<div class="wwgrp">
									<s:property value="%{component.name}" />
								</div>
							</td>
							<td align="right" id='minValue<s:property value="%{component.code}" />' style="visibility:hidden">
								<s:if test='%{component.dataType=="M"}'>
									<s:text name="format.money">
										<s:param name="value" value="%{(classDetails.minimumValue==null)? 0: classDetails.minimumValue}" />
									</s:text>
								</s:if>
								<s:elseif test='%{component.dataType=="N"}'>
									<s:property value="%{(classDetails.minimumValue==null)? 0: classDetails.minimumValue}" />
								</s:elseif>
							</td>
							<td align="right" id='maxValue<s:property value="%{component.code}" />' style="visibility:hidden">
								<s:if test='%{component.dataType=="M"}'>
									<s:text name="format.money">
										<s:param name="value" value="%{(classDetails.maximumValue==null)? 0: classDetails.maximumValue}" />
									</s:text>
								</s:if>
								<s:elseif test='%{component.dataType=="N"}'>
									<s:property value="%{(classDetails.maximumValue==null)? 0: classDetails.maximumValue}" />
								</s:elseif>
							</td>
							<td align="right">
								<s:if test='%{component.dataType=="M"}'>
									<s:text name="format.money">
										<s:param name="value" value="%{(classDetails.defaultValue==null)? 0: classDetails.defaultValue}" />
									</s:text>
								</s:if>
								<s:elseif test='%{component.dataType=="N"}'>
									<s:property value="%{(classDetails.defaultValue==null)? 0: classDetails.defaultValue}" />
								</s:elseif>
							</td>
							<td width="25%" align="right">
								<s:if test='%{component.type=="P"}'>
									<s:if test='%{component.dataType=="S"}'>
										<s:textarea
											name="strData.comp%{component.code}"
											requiredLabel="true"
											rows="6"
											cols="16"
											cssStyle="resize: none"
											value="%{value}"
										/>
									</s:if>
									<s:else>
										<s:textfield 
											name="strData.comp%{component.code}" 
											value="%{(component.dataType==\"S\")? value: getText('{0, number, #.##;-#.##}', {value}).trim()}"
											cssClass='%{(component.dataType=="M")? "compMoney": ""}'
											compType='%{component.type}' 
										/>
									</s:else>
								</s:if>
								<s:elseif test='%{component.type=="R"}'>
									<s:hidden 
										name="strData.comp%{component.code}" 
										value="%{(component.dataType==\"S\")? value: getText('{0, number, #.##;-#.##}', {value}).trim()}"
										compType='%{component.type}' 
									/>
									<s:if test='%{component.dataType=="M"}'>
										<s:text name="format.money">
											<s:param name="value" value="%{(value==null)? 0: value}" />
										</s:text>
									</s:if>
									<s:else>
										<s:property value="%{(component.dataType==\"S\")? value: getText('{0, number, #.##;-#.##}', {value}).trim()}" />
									</s:else>
								</s:elseif>
								<s:hidden name="strData.type%{component.code}" value="%{component.dataType}" />
							</td>
						</tr>
					</s:iterator>
					
					<tr><td colspan="6">&nbsp;</td></tr>
				</s:iterator>
			</tbody>
		</table>
	</fieldset>
	
	
	<s:hidden name="id" />
	<s:hidden name="noAccount" />
	<s:hidden name="codeClass" />
	<s:hidden name="dateCommitment" />
	<s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
	<s:hidden name="idMaintainedSpv" />
	
	<s:hidden name="mode" value="1" disabled="true" /> <%-- for edit and change Mitra Pasti package purpose --%>
	<s:hidden name="tmpMoney" cssClass="compMoney" disabled="true" />
	
	<s:token name="token" />
</s:form>


<script type="text/javascript">
	$(function() {
		$("#frmComponent > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("width", function() {
					var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
					return width_;
				});
		$("#tblCommitment, #tblCashback").css("width", "90%");
		$("#frmComponent").find(".compMoney").autoNumeric("init");
		
		
		changeAction("frmComponent", "27401", "_" + rdb.current);
		
		
		if (rdb.current == "inquiry")
			$("#btnProcess").hide();
		else
			$("#btnProcess").show();
		
		
		if ((rdb.current == "inquiry") || (rdb.current == "delete") || 
					((rdb.current == 'edit') && ($("#frmMain_hdFlagChangePackage").attr("value") == "0"))) {
			$("input[name^='chkComp']").hide();
			$("#frmComponent").find("input[name^='strData']").attr("readonly", true);
			
			<%-- remove all component rows which check logic component is not checked --%>
			$("#frmComponent").find("input[name^='chkComp']").each(function(index) {
				if ($(this).attr("checked") != "checked") {
					var logicCode = $(this).attr("name").substr("chkComp".length);
					var $parent = $(this).closest("tr");
					var childCount = $("#childCount" + logicCode).val();
					
					for (var i=0; i<childCount; i++)
						$parent.next().remove();
					
					$parent.remove();
				}
			});
			
			<%-- change all text input into text only --%>
			$("#frmComponent").find("input[name^='strData'][type!=hidden], textarea[name^='strData']").each(function(index) {
				$(this).parent().append($(this).val());
				$(this).remove();
			});
			
		}
		else if (
			(rdb.current == "add") || 
			((rdb.current == "edit") && ($("#frmMain_codeClass").val().trim() != $("#frmMain_codeClassOld").val().trim()))) /* update Mitra Pasti package */ 
		{
				<%-- readonly component  --%>
				$("#frmComponent").find("input[compType='R']").each(function(index) {
					var parent = $(this).parent();
					var value = parent.prev().html();
					
					$(this).attr("value", value);
					parent.append(value);
				});
		}
		else {
			<%-- for edit, check the checkbox if data exit, uncheck if otherwise --%>
			$("#frmComponent").find("input[name^='chkComp']").each(function(index, elem) {
				chkCompClicked($(this)[0]);
			});
		}
		
		$("#frmComponent_id").val($("#frmMain_id").val());
		$("#frmComponent_noAccount").val($("#frmMain_noAccount").val());
		$("#frmComponent_codeClass").val($("#frmMain_codeClass").val());
		$("#frmComponent_dateCommitment").val($("#frmMain_dateCommitment").val());
		
		filterKeys($("#frmComponent").find("input[type='text']"), function(e, elemText_, char_) {
			if (!char_.match(/[0-9\.]/))
				e.preventDefault();
		});
	});
	
	function chkCompClicked(item) {
		var chkCompId = item.name.substr("chkComp".length);
		$("#frmComponent").find("input[name^='strData.comp" + chkCompId + "']").each(function(index) {
			var compName = $(this).attr("name");
			var compNo = compName.substr("strData.comp".length);
			
			if (compName.substr(0, compName.length-2) == ("strData.comp") + chkCompId) {
				$(this).attr("disabled", item.checked? null: "true");
				$("input[name='strData.type" + compNo + "']").attr("disabled", item.checked? null: "true"); // attr disabled of strData.type depend on enable/disabled of strData.comp
				if (item.checked == false)
					$(this).attr("value", "");
			}
		});
	}

</script>