"use strict";
var BookStatus;
(function (BookStatus) {
    BookStatus["Read"] = "Read";
    BookStatus["ReRead"] = "Re-read";
    BookStatus["DNF"] = "DNF";
    BookStatus["CurrentlyReading"] = "Currently reading";
    BookStatus["ReturnedUnread"] = "Returned Unread";
    BookStatus["WantToRead"] = "Want to read";
})(BookStatus || (BookStatus = {}));
var BookFormat;
(function (BookFormat) {
    BookFormat["Print"] = "Print";
    BookFormat["PDF"] = "PDF";
    BookFormat["Ebook"] = "Ebook";
    BookFormat["AudioBook"] = "AudioBook";
})(BookFormat || (BookFormat = {}));
// Book class with required methods
class Book {
    constructor(title, author, numberOfPages, status, price, pagesRead, format, suggestedBy) {
        this.title = title;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.status = status;
        this.price = price;
        this.pagesRead = pagesRead;
        this.format = format;
        this.suggestedBy = suggestedBy;
        this.finished = pagesRead === numberOfPages;
    }
    currentlyAt() {
        return Math.floor((this.pagesRead / this.numberOfPages) * 100);
    }
    deleteBook() {
        const books = BookManager.getBooks().filter(b => b.title !== this.title);
        localStorage.setItem('books', JSON.stringify(books));
        BookManager.displayBooks();
    }
}
// Manager class to handle books
class BookManager {
    static getBooks() {
        const books = localStorage.getItem('books') || '[]';
        return JSON.parse(books).map((b) => new Book(b.title, b.author, b.numberOfPages, b.status, b.price, b.pagesRead, b.format, b.suggestedBy));
    }
    static addBook(book) {
        const books = this.getBooks();
        books.push(book);
        localStorage.setItem('books', JSON.stringify(books));
        this.displayBooks();
    }
    static displayBooks() {
        const books = this.getBooks();
        const booksList = document.getElementById('booksList');
        const statsElement = document.getElementById('stats');
        // Update stats
        const totalBooks = books.length;
        const totalPagesRead = books.reduce((sum, book) => sum + book.pagesRead, 0);
        const booksFinished = books.filter(b => b.finished).length;
        statsElement.innerHTML = `
            <p>Total Books: ${totalBooks}</p>
            <p>Total Pages Read: ${totalPagesRead}</p>
            <p>Books Finished: ${booksFinished}</p>
        `;
        // Display books
        booksList.innerHTML = books.map(book => `
            <div class="book-card">
                <h3>${book.title}</h3>
                <p>Author: ${book.author}</p>
                <p>Progress: ${book.currentlyAt()}%</p>
                <p>Status: ${book.status}</p>
                <p>Format: ${book.format}</p>
                <p>Pages: ${book.pagesRead}/${book.numberOfPages}</p>
                <div class="progress-bar">
                    <div class="progress" style="width: ${book.currentlyAt()}%"></div>
                </div>
                <button onclick='${book.deleteBook.bind(book)}'>Delete</button>
            </div>
        `).join('');
    }
}
// Initialize application
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('bookForm');
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const book = new Book(formData.get('title'), formData.get('author'), Number(formData.get('numberOfPages')), formData.get('status'), Number(formData.get('price')), Number(formData.get('pagesRead')), formData.get('format'), formData.get('suggestedBy'));
        BookManager.addBook(book);
        form.reset();
    });
    BookManager.displayBooks();
});
