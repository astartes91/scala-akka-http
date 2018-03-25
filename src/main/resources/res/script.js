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
        $("#createBookButton").click(
            function () {
                var bookCode = $("#bookCode").val()
                var bookTitle = $("#bookTitle").val()
                var bookAuthor = $("#bookAuthor").val()
                var bookYear = $("#bookYear").val()
                var bookGenre = $("#bookGenre").val()
                var bookRating = $("#bookRating").val()

                const url =
                    "/bookstore/books" +
                    "?code=" + bookCode +
                    "&title=" + bookTitle +
                    "&authorCode=" + bookAuthor +
                    "&year=" + bookYear +
                    "&genre=" + bookGenre +
                    "&rating=" + bookRating;
                $.post(
                    url,
                    function (data) {
                        alert("Book created!")
                        window.location = "/bookstore/books"
                    }
                )
            }
        )
    }
)