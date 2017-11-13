FROM cassandra

COPY entrypoint-wrap.sh /entrypoint-wrap.sh
RUN chmod 755 entrypoint-wrap.sh
ENTRYPOINT ["/entrypoint-wrap.sh"]
CMD ["cassandra", "-f"]
