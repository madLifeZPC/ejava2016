/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {

    var appendToTable = function (id, data) {
        var all_body = "";
        var all_row = "";
        $.each(data, function (k, v) {
            all_row += "<td>" + v + "</td>";
        });
        all_body += "<tr>" + all_row + "</tr>";
        $("#" + id).append(all_body);
    }

    var writeToNoticeboard = function (data) {
        for (var i = 0; i < data.length; i++) {
            appendToTable("allPosts", data[i]);
            if (data[i].category == "Social") {
                appendToTable("socialPosts", data[i]);
            } else if (data[i].category == "For Sale") {
                appendToTable("forsalePosts", data[i]);
            } else if (data[i].category == "Jobs") {
                appendToTable("jobsPosts", data[i]);
            } else {
                appendToTable("tuitionPosts", data[i]);
            }
        }
    }

    var wirteMessageToNoticeboard = function (data) {
        appendToTable("allPosts", data);
        if (data.category == "Social") {
            appendToTable("socialPosts", data);
        } else if (data.category == "For Sale") {
            appendToTable("forsalePosts", data);
        } else if (data.category == "Jobs") {
            appendToTable("jobsPosts", data);
        } else {
            appendToTable("tuitionPosts", data);
        }
    }

    var url = "ws://localhost:8080/week04_ca/noticeboard";
    var socket = new WebSocket(url);

    socket.onmessage = function (evt) {
        var msg = JSON.parse(evt.data);
        if( msg.title ){
            wirteMessageToNoticeboard(msg);
        }
        else{
            writeToNoticeboard(msg);
        }
        console.log(msg);

    }

    socket.onopen = function (evt) {
        console.log("open");
    }

    socket.onclose = function () {
        console.log("close");
    }

});




