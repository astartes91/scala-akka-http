$(document).ready(
    function () {
        $("#createAuthorButton").click(
            function () {
                var code = $("#authorCode").val()
                var name = $("#authorName").val()
                $.post(
                    "/bookstore/authors?code=" + code + "&name=" + name,
                    function (data) {
                        alert("Author created!")
                        window.location = "/bookstore/authors"
                    }
                )
            }
        )
    }
)