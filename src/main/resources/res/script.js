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
        $("#filterBooks").click(
            function () {
                const minRating = $("#minRating").val();
                const maxRating = $("#maxRating").val();
                if(minRating || maxRating){
                    var url = "/bookstore/books?" +
                        (minRating ? ("minRating=" + minRating) : "") +
                        (minRating && maxRating ? "&" : "") +
                        (maxRating ? "maxRating=" + maxRating : "");
                    $.get(
                        url,
                        function (data) {
                            $("#booksTable > tbody > tr:not(first-child)").slice(1).remove()
                            $("#booksTable > tbody > tr:first-child").after(data)
                        }
                    )
                }
            }
        )
        $("#clearFilter").click(
            function () {
                window.location = "/bookstore/books"
            }
        )
    }
)