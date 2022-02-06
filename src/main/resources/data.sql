
INSERT INTO account (account_id, account_number, account_balance, currency, created, updated, version) VALUES
  (1, 111, 50.00, 'DKK', current_timestamp, current_timestamp, 0);


INSERT INTO ledger(account_id, amount, transaction_type, currency, transaction_date, version) VALUES
    (1, 100.00, 'CREDIT', 'DKK', current_timestamp, 0 );