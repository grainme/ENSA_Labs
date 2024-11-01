# Book Reading Tracker - Exercise Summary

## Exercise Questions & Answers

### Q1: Create an HTML form that registers new books using TailwindCSS
#### Done:
- Created HTML form with required fields
- Added TailwindCSS for styling
- Implemented input fields for:
  - Title (string)
  - Author (string)
  - Number of pages (number)
  - Status (enum dropdown)
  - Price (number)
  - Pages read (number)
  - Format (enum dropdown)
  - Suggested by (string)

### Q2: Create Book class
#### Done:
- Implemented Book class with required methods:
  - Constructor
  - currentlyAt() method
  - deleteBook() method
- Added automatic finished status update

### Q3: Create Status and Format Enums
#### Done:
- Created BookStatus enum with values:
  - Read
  - Re-read
  - DNF
  - Currently reading
  - Returned Unread
  - Want to read
- Created BookFormat enum with values:
  - Print
  - PDF
  - Ebook
  - AudioBook

### Q4: Create tracking webpage
#### Done:
- Created webpage displaying:
  - List of all books
  - Reading progress percentage per book
  - Total books count
  - Total pages read

## Project Setup
Commands needed to run the project:
```bash
mkdir book-tracker
cd book-tracker
npm init -y
npm install typescript --save-dev
npm install -g http-server
npx tsc --init
mkdir src
touch src/app.ts
touch index.html
npx tsc
http-server
```

## Access
Open browser and go to: http://localhost:8080
