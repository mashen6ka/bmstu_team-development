#!/bin/bash

PROPERTIES=./application/server/src/main/resources/application.properties

echo springdoc.swagger-ui.path=/swagger-ui.html > $PROPERTIES
echo jwt.secret.access=e/8ZBKG1mdHVWfqe8DbZpOj3XLVVeBIXp0ZUHo+89VN4dOQGZTtO8ufL9KBNG7Axoa3nSd6cvPhJQDcv2MwfBg== >> $PROPERTIES
echo jwt.secret.refresh=bPrd0wM4E7vVk/r0MyCVMYsEVAccAQNNTDsgwhjeOrHaYIzE8V1Lo/0e1TMj3DtMX3Rk7bTgwognZASWhU1W7A== >> $PROPERTIES