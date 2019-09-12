<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="branch_" scope="request">${param.branch}</s:set>
<s:set var="currency_" scope="request">${param.currency}</s:set>
<s:set var="txnId_" scope="request">${param.txnId}</s:set>

<s:url var="urlDenom" action="COMDenom_list" namespace="/json" escapeAmp="false"></s:url>
<s:url var="urlGrid" action="21506_list" namespace="/json" escapeAmp="false"></s:url>
<s:url var="urlNext" action="21506_doChange" namespace="/json" escapeAmp="false"></s:url>

<s:actionerror name="actionError" />

<sjg:grid id="gridtableDenom" 
        caption="Search Result for Denom"
        title="Search Result for Denom"
          dataType="json" 
        href="%{urlGrid}"
        requestType="POST"
        pager="true"
        pagerPosition="right"
        gridModel="gridTemplate" 
        rowNum="15"
        rownumbers="false"
        altRows="true" 
        autowidth="true"
        navigator="true" 
        navigatorAddOptions="{reloadAfterSubmit:true}"
        navigatorEditOptions="{reloadAfterSubmit:false}"
          navigatorAdd="true"
        navigatorDelete="true"
        navigatorEdit="true"
        navigatorRefresh="false"
        navigatorSearch="false"
        onSelectRowTopics="selectRowDenom" 
        onEditInlineSuccessTopics="oneditdenom"
        onGridCompleteTopics="gridCompletedDenom"
          editurl='%{urlNext}'
        >
    <sjg:gridColumn 
        name="denomId" 
        title="Denom ID" 
        index="denomId" 
        width="50" 
        align="center" 
        sortable="false" 
        hidden="true" />
    <sjg:gridColumn 
        name="namDenom" 
        index="denomName" 
        title="Denom" 
        width="50" 
        align="center" 
        sortable="false" 
        editoptions="{ dataUrl : '%{urlDenom}' }"/>
    <sjg:gridColumn 
        name="amount" 
        title="CP Amount Request" 
        width="50" 
        align="center" 
        sortable="false" 
        editable="true"/>
    <sjg:gridColumn 
        name="piece" 
        title="CP Pieces Request" 
        width="50" 
        align="center" 
        sortable="false" 
        editable="true"/>

    </sjg:grid>

    <script type="text/javascript">
        jQuery(document).ready(function() {
        /* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */
        if (navigator.appName.indexOf("Internet Explorer") > -1) {
            $("*[role='button']", $("#divButtons")).css("position", "static");
        }

            $("#gridtableDenom").unsubscribe("gridCompletedDenom");
            $("#gridtableDenom").subscribe("gridCompletedDenom", function(event, data) {

                if ($("#gridtableDenom_pager_left #note").length == 0) {
                var recordTable = $('#gridtableDenom').jqGrid('getGridParam', 'records');

                    if (recordTable == 0) {
                        $("#gridtableDenom_pager_left").append("<span id='note' style='color:red'>No records found</span>");
                    } else {
                    $("#gridtableDenom_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
                    }
                }
                var gC = "2";
                $("#frmMain_gridClear").val(gC.toString());
            });

        $("#gridtableDenom").unsubscribe("selectRowDenom");
        $("#gridtableDenom").subscribe("selectRowDenom", function(event, data) {
            $("#gridtableDenom_pager_left #note").hide();
            $("#frmDetailDelete_id").val($(data).jqGrid("getCell", event.originalEvent.id, "denomId"));
        });
                //alert('tab1');
                $("#gridtableDenom").jqGrid('setGridParam', {
                datatype: 'local',
                    postData: {
                        'branch': function() {
                            return '<s:property value="%{#request.branch_}" />';
                        },
                        'vendorID': function() {
                    return $("#inquiryDenom_vendorID").val();
                },
                'denomId' : function() {
                    return $("#frmDetailDelete_id").val();
                        },
                        'currency': function() {
                            return '<s:property value="%{#request.currency_}" />';
                },
                'txnId': function() {
                    return '<s:property value="%{#request.txnId_}" />';
                        }
                    },
                    shrinkToFit: false,
                    ondblClickRow: function (rowid) {
                    if (rowid && rowid !== lastSel) {
                        $('#list').restoreRow(lastSel);
                        lastSel = rowid;
                    }
                    $(this).jqGrid("editGridRow", rowid, {
                        keys: true,
                        addCaption: "Add Record",
                        editCaption: "Edit Record",
                        bSubmit: "Submit",
                        bCancel: "Cancel",
                        bClose: "Close",
                        saveData: "Data has been changed! Save changes?",
                        bYes: "Yes",
                        bNo: "No",
                        bExit: "Cancel",
                        width: window.screen.width / 3,
                        top: window.screen.height / 4,
                        left: window.screen.availWidth / 3,
                    editUrl: '%{urlNext}',
                    cellEdit: true
                    });
                    // debugger;
                    //                    var grid = $('#list');
                    //                    var myRowData = grid.getRowData(rowid);
                    //                    grid.editRow(rowid, true);
                    //alert(myRowData['Specialty'] + ' ' + myRowData['SpecialtyName']);
                },
                });
                $("#gridtableDenom").trigger('reloadGrid');
                //$("#gridtableCIF").trigger('reloadGrid', [{current: true}]);
        //}
        });
</script>
