var firstFieldErrorPosition = null;

if (typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, ''); 
    };
};


function clearErrorMessagesCSS(form) {
    firstFieldErrorPosition = null;
    // clear out any rows with an "errorFor" attribute
    var i, divs = form.getElementsByTagName("div"), paragraphsToDelete = [];

    for (i=0; i<divs.length; i++) {
        var p = divs[i];
        if (p.getAttribute("errorFor")) {
            paragraphsToDelete.push(p);
        }
    }

    // now delete the paragraphsToDelete
    for (i=0; i<paragraphsToDelete.length; i++) {
        var r = paragraphsToDelete[i];
        var parent = r.parentNode;
        parent.removeChild(r);
    }
}

function clearErrorMessagesCSS_single(form,message) {
    firstFieldErrorPosition = null;
    // clear out any rows with an "errorFor" attribute
    var i, divs = form.getElementsByTagName("div"), paragraphsToDelete = [];

    for (i=0; i<divs.length; i++) {
        var p = divs[i];
        if (p.getAttribute("errorFor")) {
			console.log("CSS "+p);
			console.log("CSS attribute "+ p.getAttribute("errorFor"));
			if(p.getAttribute("errorFor") == message){
				paragraphsToDelete.push(p);
			}
        }
    }

    // now delete the paragraphsToDelete
    for (i=0; i<paragraphsToDelete.length; i++) {
        var r = paragraphsToDelete[i];
        var parent = r.parentNode;
        parent.removeChild(r);
    }
}

function clearErrorMessages(form) {
    clearErrorMessagesCSS(form);
}

function clearErrorLabelsCSS(form) {
    // set all labels back to the normal class
    var i, labels = form.getElementsByTagName("label");
    for (i=0; i<labels.length; i++) {
        var label = labels[i];
        if (label) {
            if (label.getAttribute("class") === "errorLabel") {
                label.setAttribute("class", "label");//standard way.. works for ie mozilla
                label.setAttribute("className", "label"); //ie hack cause ie does not support setAttribute
            }
        }
    }
}

function clearErrorLabels(form) {
    clearErrorLabelsCSS(form);
}

function findWWGrpNode(elem) {
    while (elem.parentNode) {
        elem = elem.parentNode;

        if (elem.className && elem.className.match(/wwgrp/)) {
            return elem;
        }
    }
    return null;
}

function findPreviousWWGrpNode(elem) {
    var enclosingElement;
    var tempElement = null;

    while (elem.parentNode) {
        elem = elem.parentNode;

        if (elem.className && elem.className.match(/wwgrp/)) {
            enclosingElement = elem.parentNode.parentNode;
            if (enclosingElement) {
                var elems = enclosingElement.getElementsByTagName("div");
                for (var i=0; i<elems.length; i++) {
                    if (elems[i].className && elems[i].className.match(/wwgrp/)) {
                        if (elems[i]==elem)
                            return tempElement;
                        
                        tempElement = elems[i];
                    }
                }
            }
        }
    }
    return null;
}

function findWWCtrlNode(enclosingDiv) {
    var elems = enclosingDiv.getElementsByTagName("div");
    for (var i=0; i<elems.length; ++i) {
        if (elems[i].className && elems[i].className.match(/(wwlbl|wwctrl)/)) {
            return elems[i];
        }
    }

    elems = enclosingDiv.getElementsByTagName("span");
    for (var i=0; i<elems.length; ++i) {
        if (elems[i].className && elems[i].className.match(/(wwlbl|wwctrl)/)) {
            return elems[i];
        }
    }
    return enclosingDiv.getElementsByTagName("span")[0];
}

//find field position in the form
function findFieldPosition(elem) {
    if (!elem.form) {
        alert("no form for " + elem);
    }

    var form = elem.form;
    for (var i=0; i<form.elements.length; i++) {
        if (form.elements[i].name === elem.name) {
            return i;
        }
    }
    return null;
}

function addErrorCSS(e, errorText) {
    try {
        if (!e) {
            return; //ignore errors for fields that are not in the form
        }
        var elem = (e.type ? e : e[0]); //certain input types return node list, while we single first node. I.e. set of radio buttons.
        var enclosingDiv = findWWGrpNode(elem); // find wwgrp div/span
        var prevEnclosingDiv = findPreviousWWGrpNode(elem); // find previous wwgrp div/span

        //try to focus on first field
        var fieldPos = findFieldPosition(elem);
        if (fieldPos !== null
                && (firstFieldErrorPosition === null || firstFieldErrorPosition > fieldPos)) {
            firstFieldErrorPosition = fieldPos;
        }

        if (!enclosingDiv) {
            alert("Could not validate: " + e.id);
            return;
        }
        
        var error;
        var errorDiv;
        var firstCtrNode;
        
        var label = prevEnclosingDiv.getElementsByTagName("label")[0];
        if (label) {
            label.setAttribute("class", "errorLabel"); //standard way.. works for ie mozilla
            label.setAttribute("className", "errorLabel"); //ie hack cause ie does not support setAttribute
            
            
            errorDiv = document.createElement("div");
            firstCtrNode = findWWCtrlNode(prevEnclosingDiv); // either wwctrl_ or wwlbl_

            errorDiv.setAttribute("class", "errorMessage"); //standard way.. works for ie mozilla
            errorDiv.setAttribute("className", "errorMessage"); //ie hack cause ie does not support setAttribute
            errorDiv.setAttribute("errorFor", elem.id);
            errorDiv.appendChild(document.createElement("br"));
            if (navigator.appName === 'Microsoft Internet Explorer') {
                prevEnclosingDiv.insertBefore(errorDiv);
            } else {
                prevEnclosingDiv.insertBefore(errorDiv, firstCtrNode);
            }
        }

        firstCtrNode = findWWCtrlNode(enclosingDiv); // either wwctrl_ or wwlbl_

        error = document.createTextNode(errorText);
        errorDiv = document.createElement("div");

        errorDiv.setAttribute("class", "errorMessage"); //standard way.. works for ie mozilla
        errorDiv.setAttribute("className", "errorMessage"); //ie hack cause ie does not support setAttribute
        errorDiv.setAttribute("errorFor", elem.id);
        errorDiv.appendChild(error);
        if (!firstCtrNode && navigator.appName === 'Microsoft Internet Explorer') {
            enclosingDiv.insertBefore(errorDiv);
        } else {
            enclosingDiv.insertBefore(errorDiv, firstCtrNode);
        }
    } catch (err) {
        alert("An exception occurred: " + err.name + ". Error message: "
                + err.message);
    }
}

function addError(e, errorText) {
    addErrorCSS(e, errorText);
}

//focus first element
var StrutsUtils_showValidationErrors = StrutsUtils.showValidationErrors;
StrutsUtils.showValidationErrors = function(form, errors) {
    StrutsUtils_showValidationErrors(form, errors);
    if (firstFieldErrorPosition !== null
            && form.elements[firstFieldErrorPosition].focus) {
        form.elements[firstFieldErrorPosition].focus();
    }
};


function messageBox(type, message, onCloseFunction) {
    var title = "";
    var icon = "";
    
    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    }
    else if (type == 3) {
        title= "Information";
        icon = "ui-icon-info";
    }
    
    
    var $current = $("#divMessage");
    var $clone = $current.clone();
    var $parent = $current.parent();
    var $prevSibling = $current.prev();
    
    if ($current.length) {
        $current
        .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
        .dialog({
            autoOpen: false,
            modal: true,
            title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
            resizable: false,
            buttons: {
                Ok: function() {
                    if ((onCloseFunction != undefined) && (typeof onCloseFunction == 'function'))
                        onCloseFunction();
                    $(this)
                        .dialog("close")
                        .dialog("destroy")
                        .remove();
                }
            },
            close: function(e) {
                if ($prevSibling.length)
                    $prevSibling.after($clone);
                else
                    $parent.append($clone);
            }
        })
        .dialog("open");
    }
};


/* filter keys */
var filterKeys = function(elem, func) {
    var keyPass = false;
    
    $(elem)
        .unbind('keydown')
        .bind('keydown', function(e) {
            var evt = e? e: event;
            var key_ = (evt.charCode)? evt.charCode: evt.keyCode;
            keyPass = false;
            
            if (evt.ctrlKey) {
                if (key_ != 45) { /* only receive insert when press ctrl*/
                    evt.preventDefault();
                }
                keyPass = true; 
            }
            else if (evt.shiftKey && (key_ == 45)) /* shift insert */
                keyPass = true;
            else if ("8|37|39|46".indexOf(key_) > -1) /* backspace, left, right, delete */
                keyPass = true;
        })
        .unbind('keypress')
        .bind('keypress', function(e) {
            if (keyPass)
                return true;
            
            var charCode = (e.charCode)? e.charCode: e.keyCode;
            var ch = String.fromCharCode(charCode);
            if (func != undefined)
                func(e, $(this).val(), ch);
        });
};

function filterKeysPhone() {
    filterKeys(".cls-phone", function(e, elemText_, char_) {
        if (elemText_ == '') {
            if (!char_.match(/[\+0-9(]/))
                e.preventDefault();
        }
        else {
            if (!char_.match(/[0-9() ]/))
                e.preventDefault();
        }
    });
};

function filterKeysMoney() {
    filterKeys(".cls-money", function(e, elemText_, char_) {
        if (!char_.match(/[0-9\.\,]/))
            e.preventDefault();
    });
};

function filterKeysNumeric() {
    filterKeys(".cls-numeric", function(e, elemText_, char_) {
        if (!char_.match(/[0-9]/))
            e.preventDefault();
    });
};

function filterKeysNumericSpace() {
    filterKeys(".cls-numeric-spc", function(e, elemText_, char_) {
        if (!char_.match(/[0-9] /))
            e.preventDefault();
    });
};

function filterKeysAlphabet() {
    filterKeys(".cls-alphabet", function(e, elemText_, char_) {
        if (!char_.match(/[a-z]/i))
            e.preventDefault();
    });
};

function filterKeysAlphabetNumeric() {
    filterKeys(".cls-alphabet-numeric", function(e, elemText_, char_) {
        if (!char_.match(/[0-9a-zA-Z ]/))
            e.preventDefault();
    });
};
function filterKeysAlphabetSpace() {
    filterKeys(".cls-alphabet-spc", function(e, elemText_, char_) {
        if (!char_.match(/[a-z\. ]/i))
            e.preventDefault();
    });
};

function filterKeysSpecifiedRegex(class_, regexp_) {
    filterKeys("." + class_, function(e, elemText_, char_) {
        if (!char_.match(regexp_, "i"))
            e.preventDefault();
    });
}



function requiredStringValidation(form, elementName, errorMessage, continueValidation) {
    var err = false;
    
    if (form.elements[elementName]) {
        field = form.elements[elementName];
		console.log("element "+field.value+" &&&&&& ");
        if (continueValidation && (field.value != null) && ((field.value == "") || (field.value.replace(/^\s+|\s+$/g, "").length == 0))) {
            addError(field, errorMessage);
			console.log("requiredStringValidation "+field.value+" & "+errorMessage);
            err = true;
        }
    }
	console.log("RET :" +err);
    return err;
}

function requiredStringValidation2(form, elementName,elementHiddenName, errorMessage, continueValidation) {
    var err = false;
    
    if (form.elements[elementName]) {
        field = form.elements[elementName];
		field2 = form.elements[elementHiddenName];
		console.log("element "+field2.value+" &&&&&& ");
        if (continueValidation && (field2.value != null) && ((field2.value == "") || (field2.value.replace(/^\s+|\s+$/g, "").length == 0))) {
            addError(field, errorMessage);
			console.log("requiredStringValidation "+field.value+" & "+errorMessage);
            err = true;
        }
    }
	console.log("RET :" +err);
    return err;
}
    
function specificStringValidation(form, elementName, stringtoCompare, continueValidation) {
    var err = false;
    //frmMain_labelmarStat
    if (form.elements[elementName]) {
        field = form.elements[elementName];
        //alert("VALUE :" +field.value);
        if (continueValidation && (field.value != null) && (field.value == stringtoCompare)) {
            err = true;
        } else if(continueValidation && (field.value != null) && (field.value != "")) {
            err = false;
        }
    }
    return err;
}
// form, element to validate, value Element, Error Message, element appear, element appear value
function fieldCompareValidation(form,elementValidate,elemValue,errorMessage, elementName, elementVal, continueValidation) {
    var err = false;
    //frmMain_labelmarStat
    if (form.elements[elementValidate]) {
        if(form.elements[elementName]){
            field = form.elements[elementName];
            fieldC = form.elements[elementValidate];
            //alert("|" + field.value + "|" + elementVal + "|");
            //alert("|" + fieldC.value + "|" + elemValue + "|");
            if (continueValidation && (fieldC.value == elemValue) && (field.value == elementVal)) {
                addError(field, errorMessage);
                addError(fieldC, errorMessage);
                //alert("VALUE :" + fieldC.value);
                err = true;
            } else if (continueValidation && (field.value != elemValue) && (field.value != elementVal)) {
                //alert("VALUE 2:" + fieldC.value);
                err = false;
            }
        }
    }
    return err;
}

function regexStringValidation(form, elementName, errorMessage, continueValidation, regexp) {
    var err = false;
    if (form.elements[elementName]) {
        field = form.elements[elementName];
		console.log("INI FIELD VALUE 22222 ="+field.value);
        if (continueValidation && (field.value != null) && (field.value != "")) {
		console.log("INI FIELD VALUE ="+field.value);
            var regex = new RegExp(regexp); 
			console.log("REGEX ="+regex);
            if(field.value.match(regex)){
                err = false;
				console.log("INI FIELD ERROR 1 ="+err);
            } else {
            addError(field, errorMessage);
            err = true;
			console.log("INI FIELD ERROR 2 ="+err);
        }
        }
    }    
    return err;
}

function regexStringValidation2(form, elementName,elementHiddenName, errorMessage, continueValidation, regexp) {
    var err = false;
    if (form.elements[elementName]) {
        field = form.elements[elementName];
		field2 = form.elements[elementHiddenName];
		console.log("INI FIELD VALUE 2333 ="+field2.value);
        if (continueValidation && (field2.value != null) && (field2.value != "")) {
		console.log("INI FIELD VALUE333 ="+field2.value);
            var regex = new RegExp(regexp); 
			console.log("REGEX3333 ="+regex);
            if(!field2.value.match(regex)){
                err = false;
				console.log("INI FIELD ERROR 1333 ="+err);
            } else {
            addError(field, errorMessage);
            err = true;
			console.log("INI FIELD ERROR 2333 ="+err);
        }
        }
    }    
    return err;
}


function lengthStringValidation(form, elementName, errorMessage, continueValidation, length) {
    var err = false;
    if (form.elements[elementName]) {
        field = form.elements[elementName];
        if (continueValidation && (field.value != null) && ((field.value == "") || (field.value.replace(/^\s+|\s+$/g, "").length == 0) || field.value.toString().length < length)) {
            addError(field, errorMessage);
            err = true;
        }
    }    
    return err;
}
/*
 * Revision : Checking range text
 * change : v00019722
 * change Date : 15-Januari-2016
 * Begin 1
 * @param {type} dtText
 * @param {type} rangeCheck
 * @returns {Date}
 */
function dateTextToDateRange(checkSplit, rangeCheck, operand, operator) {
    //alert(checkSplit);
    var arDate = checkSplit.toString().split('/');
    var dt = new Date();

    var date = parseInt(arDate[0], 10);
    var month = parseInt(arDate[1], 10);
    var year = parseInt(arDate[2], 10);

    if (operator === '-') {
        if (operand === 'Y') {
            dt.setFullYear(year - rangeCheck, month, date);
        } else if (operand === 'M') {
            dt.setFullYear(year, month - rangeCheck, date);
        } else if (operand === 'D') {
            dt.setFullYear(year, month, date - rangeCheck);
        }
    } else if (operator == '+') {
        if (operand === 'Y') {
            dt.setFullYear(year + rangeCheck, month, date);
        } else if (operand === 'M') {
            dt.setFullYear(year, month + rangeCheck, date);
        } else if (operand === 'D') {
            dt.setFullYear(year, month, date + rangeCheck);
        }
    }
    return dt;
}
function labelingValidation(form, elementName, errorMessage, continueValidation) {
    var err = false;

    if (form.elements[elementName]) {
        field = form.elements[elementName];

        if (continueValidation) {
            //alert("2");
            addError(field, errorMessage);
            err = true;
        }
    }
    return err;
}
function dateCompValidation(form, elementName, errorMessage, continueValidation, dateCheck, dateNow) {
    var err = false;

    if (form.elements[elementName]) {
        field = form.elements[elementName];

        if (continueValidation && isAfterDateTo(dateCheck.toString(), dateNow.toString())) {
            //alert("2");
            addError(field, errorMessage);
            err = true;
        }
    }
    return err;
}
function isAfterDateTo(dateText1, dateText2) {
    //alert(dateText1 + "|" + dateText2);
    var dt1 = Date.parse(dateText1);
    var dt2 = Date.parse(dateText2);
    //alert(dt1 +"+" + dt2);
    //alert(dateText1 + "|" + dateText2);
    //alert((dateText1 - dateText2));
    return ((dt1 - dt2) > 0);
}
/*
 * End 1
 */
function isAfterDate(dateText1, dateText2) {
    var dt1 = dateTextToDate(dateText1);
    var dt2 = dateTextToDate(dateText2);
    
    return ((dt1 - dt2) > 0);
}

function dateTextToDate(dtText) {
    var arDate = dtText.split("/");
    var dt = new Date();
    
    var date = parseInt(arDate[0], 10);
    var month = parseInt(arDate[1] - 1, 10);
    var year = parseInt(arDate[2], 10);
    
    dt.setFullYear(year, month, date);
    dt.setHours(0, 0, 0, 0);
    
    return dt;
}

function messageBoxOkCancel2(type, element ,message, onOkFunction, onCancelFunction) {
    var title = "";
    var icon = "";
    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    }
    else if (type == 3) {
        title = "Information";
        icon = "ui-icon-info";
    }    
    $("#" + element)
        .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
        .dialog({
        open: function() {                         // open event handler
            $(this)                                // the element being dialogged
            .parent()                          // get the dialog widget element
            .find(".ui-dialog-titlebar-close") // find the close button for this dialog
            .hide();                           // hide it
        },
            modal: true,
            title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
            resizable: false,
            autoOpen:false,
            buttons: {
                Ok: function() {
                    if ((onOkFunction != undefined) && (typeof onOkFunction == 'function'))
                        onOkFunction();
                    $(this).dialog("close");
                },
                Cancel: function() {
                    if ((onCancelFunction != undefined) && (typeof onCancelFunction == 'function'))
                        onCancelFunction();
                    $(this).dialog("close");
                }
            }
        })
        .dialog("open");
};

function messageBoxOkCancelClear(type, element ,message, onOkFunction, onCancelFunction, onClearFunction) {
    var title = "";
    var icon = "";
    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    }
    else if (type == 3) {
        title = "Information";
        icon = "ui-icon-info";
    }    
    $("#" + element)
        .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
        .dialog({
        open: function() {                         // open event handler
            $(this)                                // the element being dialogged
            .parent()                          // get the dialog widget element
            .find(".ui-dialog-titlebar-close") // find the close button for this dialog
            .hide();                           // hide it
        },
            modal: true,
            title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
            resizable: false,
            autoOpen:false,
            buttons: {
                Ok: function() {
                    if ((onOkFunction != undefined) && (typeof onOkFunction == 'function'))
                        onOkFunction();
                    $(this).dialog("close");
                },
                Cancel: function() {
                    if ((onCancelFunction != undefined) && (typeof onCancelFunction == 'function'))
                        onCancelFunction();
                    $(this).dialog("close");
                },
                Clear: function() {
                    if ((onClearFunction != undefined) && (typeof onClearFunction == 'function'))
                        onClearFunction();
                    $(this).dialog("close");
                }
            }
        })
        .dialog("open");
};
function messageBoxOkCancel(type, message, onOkFunction, onCancelFunction) {
    var title = "";
    var icon = "";

    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    }
        
    $("#divMessage")
        .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
        .dialog({
        open: function() {                         // open event handler
            $(this)                                // the element being dialogged
            .parent()                          // get the dialog widget element
            .find(".ui-dialog-titlebar-close") // find the close button for this dialog
            .hide();                           // hide it
        },
            modal: true,
            title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
            resizable: false,
            autoOpen:false,
            buttons: {
                Ok: function() {
                    if ((onOkFunction != undefined) && (typeof onOkFunction == 'function'))
                        onOkFunction();
                    $(this).dialog("close");
                },
                Cancel: function() {
                    if ((onCancelFunction != undefined) && (typeof onCancelFunction == 'function'))
                        onCancelFunction();
                    $(this).dialog("close");
                }
            }
        })
        .dialog("open");
};

function messageBoxParam(div,type, message, onCloseFunction) {
    var title = "";
    var icon = "";
    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    } else if(type == 3) {
        title= "Information";
        icon = "ui-icon-info";
    }
    $("#"+div)
    .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
    .dialog({
        modal: true,
        title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
        resizable: false,
        buttons: {
            Ok: function() {
                if ((onCloseFunction != undefined) && (typeof onCloseFunction == 'function'))
                    onCloseFunction();
                $(this).dialog("close");
            }
        }
    });
};

function messageBoxPrint(div, message, onCloseFunction) {
    var title = "";
    var icon = "";
	
	title= "Information";
    icon = "ui-icon-info";

    $("#"+div)
    .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
    .dialog({
        modal: true,
        title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
        resizable: false,
        buttons: {
            Print: function() {
                if ((onCloseFunction != undefined) && (typeof onCloseFunction == 'function'))
                    onCloseFunction();
                $(this).dialog("close");
            }
        }
    });
};

function messageBoxClass(type,element, message,onCloseFunction) {
    var title = "";
    var icon = "";
    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    } else if(type == 3) {
        title= "Information";
        icon = "ui-icon-info";
    }
    $("#" + element)
    .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
    .dialog({
        open: function() {                         // open event handler
            $(this)                                // the element being dialogged
            .parent()                          // get the dialog widget element
            .find(".ui-dialog-titlebar-close") // find the close button for this dialog
            .hide();                           // hide it
        },
        modal: true,
        title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
        resizable: false,
        buttons: {
            Ok: function() {
                if ((onCloseFunction != undefined) && (typeof onCloseFunction == 'function'))
                    onCloseFunction();
                $(this).dialog("close");
            }
        }
    });
};

function waitingMessage(type, message, element) {
    var title = "";
    var icon = "";
    
    if (type == 1) {
        title = "Warning";
        icon = "ui-icon-alert";
    }
    else if (type == 2) {
        title = "Error";
        icon = "ui-icon-circle-close";
    } else if(type == 3) {
        title= "Information";
        icon = "ui-icon-info";
    }
    $("#" + element)
    .html("<table><tbody><tr><td width:'500px';><p style='text-align: center; vertical-align: middle'>" + message + "</p></td></tr><tr><td><span id='spinner' style='position:relative; vertical-align: middle;'><img src='./ajax-loader.gif' style='position:absolute; left:10px; top:0; width:200px; height:13px; z-index:99' /></span></td></tr></tbody></table><br>")
    .dialog({
        open: function() {                         // open event handler
            $(this)                                // the element being dialogged
            .parent()                          // get the dialog widget element
            .find(".ui-dialog-titlebar-close") // find the close button for this dialog
            .hide();                           // hide it
        },
        modal: true,
        title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
        resizable: false,
        width:"auto",
        height:"auto",
        minHeight:0
    });
};

