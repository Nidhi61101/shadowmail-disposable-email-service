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
| Database      | PostgreSQL / MongoDB            |
| Messaging     | RabbitMQ or Kafka (for async email queue) |
| Email Service | SMTP via Mailgun / Amazon SES / SendGrid |
| Frontend (opt)| React.js (Dashboard)            |
| DevOps (opt)  | Docker, Kubernetes, GitHub Actions |

---

## 🧠 Database Design (Sample)

### Users Table
| Field       | Type       |
|-------------|------------|
| id          | UUID       |
| email       | String     |
| created_at  | Timestamp  |

### Aliases Table
| Field       | Type       |
|-------------|------------|
| id          | UUID       |
| alias_email | String     |
| user_id     | UUID (FK)  |
| active      | Boolean    |
| expires_at  | Timestamp  |

### Emails Table
| Field       | Type       |
|-------------|------------|
| id          | UUID       |
| alias_id    | UUID (FK)  |
| subject     | String     |
| body        | Text       |
| is_spam     | Boolean    |
| received_at | Timestamp  |

---

## 🧩 ML Classifier (Brief)

- Input: Email subject + body
- Model: Logistic Regression / Naive Bayes / BERT (upgrade later)
- Output: `Spam` or `Not Spam`

We’ll train on a dataset like Enron Spam or Apache SpamAssassin to begin.