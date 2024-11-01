enum BookStatus {
    Read = "Read",
    ReRead = "Re-read",
    DNF = "DNF",
    CurrentlyReading = "Currently reading",
    ReturnedUnread = "Returned Unread",
    WantToRead = "Want to read"
}

enum BookFormat {
    Print = "Print",
    PDF = "PDF",
    Ebook = "Ebook",
    AudioBook = "AudioBook"
}

// Book class with required methods
class Book {
    finished: boolean;

    constructor(
        public title: string,
        public author: string,
        public numberOfPages: number,
        public status: BookStatus,
        public price: number,
        public pagesRead: number,
        public format: BookFormat,
        public suggestedBy: string
    ) {
        this.finished = pagesRead === numberOfPages;
    }

    currentlyAt(): number {
        return Math.floor((this.pagesRead / this.numberOfPages) * 100);
    }

    deleteBook(): void {
        const books = BookManager.getBooks().filter(b => b.title !== this.title);
        localStorage.setItem('books', JSON.stringify(books));
        BookManager.displayBooks();
    }
}

// Manager class to handle books
class BookManager {
    static getBooks(): Book[] {
        const books = localStorage.getItem('books') || '[]';
        return JSON.parse(books).map((b: any) => 
            new Book(b.title, b.author, b.numberOfPages, b.status, 
                    b.price, b.pagesRead, b.format, b.suggestedBy)
        );
    }

    static addBook(book: Book): void {
        const books = this.getBooks();
        books.push(book);
        localStorage.setItem('books', JSON.stringify(books));
        this.displayBooks();
    }

    static displayBooks(): void {
        const books = this.getBooks();
        const booksList = document.getElementById('booksList')!;
        const statsElement = document.getElementById('stats')!;
        
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
    const form = document.getElementById('bookForm') as HTMLFormElement;
    
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        
        const book = new Book(
            formData.get('title') as string,
            formData.get('author') as string,
            Number(formData.get('numberOfPages')),
            formData.get('status') as BookStatus,
            Number(formData.get('price')),
            Number(formData.get('pagesRead')),
            formData.get('format') as BookFormat,
            formData.get('suggestedBy') as string
        );
        
        BookManager.addBook(book);
        form.reset();
    });

    BookManager.displayBooks();
});
