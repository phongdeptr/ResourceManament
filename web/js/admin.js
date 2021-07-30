/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

});

function processRequest(requestID, actionProcess, requestResource, requestUser) {
    $.ajax({
        url: "MainController",
        cache: false,
        data: {
            requestId: requestID,
            action: actionProcess,
            request_resource: requestResource,
            requestUser: requestUser
        },
        success: function (results) {
            if (results !== "SUCCESS") {
                var error = JSON.parse(results);
                alert(error.unavailableToProcess);
            } else {
                location.reload();
            }
        }
    });
}