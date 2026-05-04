<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="collection.CollectionOwn" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.List" %>

<%
    CollectionOwn c = (CollectionOwn) request.getSession().getAttribute("logged");
    if (c == null) { response.sendRedirect(request.getContextPath() + "/index.jsp"); return; }
    String me = c.getOwner().getUsername();
    List<User> friends = (List<User>) request.getAttribute("friends");
    List<Object[]> pending = (List<Object[]>) request.getAttribute("pendingRequests");
    List<User> searchResults = (List<User>) request.getAttribute("searchResults");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Amici</title>
    <base href="${pageContext.request.contextPath}/views/">
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
    <script src="../jquery/jquery-3.4.1.js"></script>
    <script src="../bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <style>
        body { background-color: beige; font-family: 'Roboto', sans-serif; }
        .page-top { margin-top: 80px; }
        .section-title {
            border-bottom: 2px solid #d9534f;
            padding-bottom: 8px;
            margin-bottom: 20px;
            color: #333;
        }
        .friend-card {
            background: white;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 12px 16px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .friend-card .name { font-weight: bold; font-size: 15px; }
        .friend-card .username { color: #888; font-size: 13px; }
        .badge-pending {
            background-color: #d9534f;
            color: white;
            border-radius: 10px;
            padding: 2px 8px;
            font-size: 12px;
            margin-left: 8px;
        }
        .empty-msg { color: #aaa; font-style: italic; }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container page-top">

    <% if (message != null) { %>
    <script>
        window.onload = function() {
            swal("<%= message.contains("già") || message.contains("errore") ? "Attenzione" : "Fatto!" %>",
                 "<%= message %>",
                 "<%= message.contains("già") || message.contains("errore") ? "warning" : "success" %>");
        };
    </script>
    <% } %>

    <div class="row">

        <!-- COLONNA SINISTRA: richieste + amici -->
        <div class="col-md-6">

            <!-- RICHIESTE IN ARRIVO -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <span class="glyphicon glyphicon-envelope"></span>
                        Richieste in arrivo
                        <% if (pending != null && !pending.isEmpty()) { %>
                        <span class="badge-pending"><%= pending.size() %></span>
                        <% } %>
                    </h3>
                </div>
                <div class="panel-body">
                    <% if (pending == null || pending.isEmpty()) { %>
                    <p class="empty-msg">Nessuna richiesta in arrivo.</p>
                    <% } else { for (Object[] req : pending) { %>
                    <div class="friend-card">
                        <div>
                            <div class="name"><%= req[2] %></div>
                            <div class="username">@<%= req[1] %></div>
                        </div>
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/friends" style="display:inline">
                                <input type="hidden" name="action" value="accept">
                                <input type="hidden" name="id" value="<%= req[0] %>">
                                <button class="btn btn-success btn-sm" type="submit">
                                    <span class="glyphicon glyphicon-ok"></span> Accetta
                                </button>
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/friends" style="display:inline">
                                <input type="hidden" name="action" value="decline">
                                <input type="hidden" name="id" value="<%= req[0] %>">
                                <button class="btn btn-danger btn-sm" type="submit">
                                    <span class="glyphicon glyphicon-remove"></span> Rifiuta
                                </button>
                            </form>
                        </div>
                    </div>
                    <% } } %>
                </div>
            </div>

            <!-- LISTA AMICI -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <span class="glyphicon glyphicon-user"></span>
                        I tuoi amici
                        <% if (friends != null && !friends.isEmpty()) { %>
                        <span class="badge"><%= friends.size() %></span>
                        <% } %>
                    </h3>
                </div>
                <div class="panel-body">
                    <% if (friends == null || friends.isEmpty()) { %>
                    <p class="empty-msg">Non hai ancora amici. Cercane uno qui sotto!</p>
                    <% } else { for (User f : friends) { %>
                    <div class="friend-card">
                        <div>
                            <div class="name"><%= f.getNome() %> <%= f.getCognome() %></div>
                            <div class="username">@<%= f.getUsername() %></div>
                        </div>
                        <span class="text-success">
                            <span class="glyphicon glyphicon-ok-circle"></span> Amici
                        </span>
                    </div>
                    <% } } %>
                </div>
            </div>

        </div>

        <!-- COLONNA DESTRA: cerca utenti -->
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <span class="glyphicon glyphicon-search"></span>
                        Cerca utenti
                    </h3>
                </div>
                <div class="panel-body">
                    <form method="post" action="${pageContext.request.contextPath}/friends">
                        <input type="hidden" name="action" value="search">
                        <div class="input-group">
                            <input type="text" name="query" class="form-control"
                                   placeholder="Cerca per username..."
                                   value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>">
                            <span class="input-group-btn">
                                <button class="btn btn-warning" type="submit">
                                    <span class="glyphicon glyphicon-search"></span> Cerca
                                </button>
                            </span>
                        </div>
                    </form>

                    <% if (searchResults != null) { %>
                    <div style="margin-top: 15px;">
                        <% if (searchResults.isEmpty()) { %>
                        <p class="empty-msg">Nessun utente trovato.</p>
                        <% } else { for (User u : searchResults) {
                               boolean alreadyFriend = false;
                               if (friends != null) {
                                   for (User f : friends) {
                                       if (f.getUsername().equals(u.getUsername())) { alreadyFriend = true; break; }
                                   }
                               }
                        %>
                        <div class="friend-card">
                            <div>
                                <div class="name"><%= u.getNome() %> <%= u.getCognome() %></div>
                                <div class="username">@<%= u.getUsername() %></div>
                            </div>
                            <% if (alreadyFriend) { %>
                                <span class="text-success">
                                    <span class="glyphicon glyphicon-ok-circle"></span> Già amici
                                </span>
                            <% } else { %>
                            <form method="post" action="${pageContext.request.contextPath}/friends">
                                <input type="hidden" name="action" value="send">
                                <input type="hidden" name="receiver" value="<%= u.getUsername() %>">
                                <button class="btn btn-primary btn-sm" type="submit">
                                    <span class="glyphicon glyphicon-plus"></span> Aggiungi
                                </button>
                            </form>
                            <% } %>
                        </div>
                        <% } } %>
                    </div>
                    <% } %>

                </div>
            </div>
        </div>

    </div><!-- /.row -->
</div><!-- /.container -->

</body>
</html>
