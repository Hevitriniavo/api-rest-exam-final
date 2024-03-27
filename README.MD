# . Application spring

## - Modèle Conceptuel de Données (MCD) :

Le MCD représente les entités et leurs relations dans un schéma de données de haut niveau, en se concentrant sur les concepts et les associations entre eux.

![MCD](/mcd-mld-mpd/MCD%20.png "mcd")

## - Modèle Physique des Données (MPD):


## - Modèle Logique de Données (MLD) :

- Le MLD traduit le MCD en un schéma de base de données réel, en décrivant les tables, les colonnes et les contraintes de clés étrangères.
  
  ### Tables  et Contraintes de clés étrangères :

**Bank** (id, name, code, email)

**Category** (id, name, type)

 **BankSold** (id, value)

**HistoryBankSold** (id, value, bank_sold_date)

**Account** (id, password, last_name, first_name, email, birthday, balance, net_monthly_salary, account_number, overdraft_enabled, creation_date, last_withdrawal_date, #bank_solds_id).  
* La colonne bank_solds_id de la table Account fait référence à l'id de la table BankSold.

**Transaction** (id, amount, reason, transaction_type, comment, #account_id, #category_id)  
* Les colonnes account_id de Transaction font référence à l'id de la table Account.  
* Les colonnes category_id de Transaction font référence à l'id de la table Category.

**TransactionHistory** (id, #transaction_id, operation_date)  
* Les colonnes account_id de TransactionHistory  font référence à l'id de la table Account.

**Transfer** (id, #sender_account_id, #receiver_account_id, #bank_id, amount, reason, effective_date, status, reference)  
* Les colonnes sender_account_id et receiver_account_id de Transfer font référence à l'id de la table Account.  
* La colonne bank_id de la table Transfer fait référence à l'id de la table Bank.

**Interest** (id, #account_id, amount, interest_rate, interest_date)  
* Les colonnes account_id de InterestValid font référence à l'id de la table Account.

**HistoryInterests** (id, #account_id, amount, interest_rate, interest_date)  
* Les colonnes account_id de HistoryInterests font référence à l'id de la table Account.

**LoanValid** (id, #account_id, amount, loans_date, status)  
* Les colonnes account_id de LoanValid font référence à l'id de la table Account.

**HistoryLoans** (id, #account_id, amount, loans_date, status)  
* Les colonnes account_id de HistoryLoans font référence à l'id de la table Account.

**LoanNotValid** (id, #account_id, amount, loans_date)  
* Les colonnes account_id de LoanNotValid font référence à l'id de la table Account.



