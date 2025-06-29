# ShadowMail: Disposable Email + Smart Spam Classifier 🕵️‍♀️📧

**ShadowMail** is a privacy-first disposable email service with smart spam detection powered by machine learning. It allows users to generate temporary email addresses (aliases) that forward emails to their real inbox, while intelligently filtering spam in real-time.

---

## 🌟 Features

- 🛡️ **Disposable Alias Emails** – Generate temporary emails for use in signups, trials, and promotions.
- 🤖 **ML-based Spam Classifier** – Real-time filtering of spam using NLP-based classification.
- 🔒 **Privacy-focused** – Keeps your real email address hidden from marketers and spammers.
- 📬 **Email Forwarding** – Non-spam emails are forwarded to the user's real address.
- 🧹 **Auto-expiry / Manual Revoke** – Control how long an alias is active.
- 📈 **Dashboard (Optional)** – View usage stats and spam analytics.

---

## 🚀 Use Case Example

**Scenario:**  
You want to sign up for a free trial of a tool, but don't trust it with your primary email.

1. Create an alias like `nidhi.shop.shadowmail.io`.
2. Use it to sign up on any site.
3. If emails sent to this alias are spammy, ShadowMail filters them.
4. If not, they're forwarded to your real inbox.
5. You can disable or delete the alias any time.

---

## 🛠️ Tech Stack

| Layer         | Tech Used                        |
|--------------|----------------------------------|
| Backend       | Java, Spring Boot               |
| ML Spam Filter| Python (scikit-learn), FastAPI or embedded model |
| Database      | PostgreSQL          |
| Email Service | SMTP via Mailgun  |
| Frontend (opt)| React.js (Dashboard)            |
| DevOps (opt)  | Docker, Kubernetes, GitHub Actions |


## 🧩 ML Classifier (Brief)

- Input: Email subject + body
- pip install flask scikit-learn pandas
- Model: Logistic Regression / Naive Bayes / BERT (upgrade later)
- Output: `Spam` or `Ham`

## How to run it in local 
- run the table scripts required for the project present under src/resources of backend folder 
- Start the spring boot application 
- Start the python application using python app.py 
- send a test email using the script
- run the front end program using npm start 

## functionality completed till now 
 - User can generate a new shadow mail and the expiry time will appear in the snackbar
 - User can paste one of the old shadow mail generated its corresponding expiry will be printed in the snackbar 
 - User can then view the emails received by the shadow mail address 
 - If the emails are not present/ email is expired corresponding error will be printed in the UI