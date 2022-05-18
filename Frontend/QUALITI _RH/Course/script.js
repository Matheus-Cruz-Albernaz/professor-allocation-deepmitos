const baseUrl = "http://professor-allocation.herokuapp.com/courses"
const listContainer = document.getElementById("list-courses");
const btnConfirmar = document.getElementById("confirm");
const btnAdd = document.getElementById("btnAdd");

let id = 0;

async function getlist() {
    const response = await fetch(baseUrl);

    if (!response.ok){
        console.error("Houve um erro, status: " + response.status);
    }

    return await response.json();
}

function handleClickAdd() {
    id = 0;
    document.getElementById("textModal").textContent = "Cadastrar novo curso";
    document.getElementById("confirm").textContent = "Salvar";
}
function handleClickEdit(id) {
    id = id;
    document.getElementById("textModal").textContent = "Alterar novo curso";
    document.getElementById("confirm").textContent = "Salvar";

    $("#modalDelete").modal("show");
}

btnAdd.addEventListener("click", handleClickAdd);

//POST
function create(courseName) {
    fetch(baseUrl, {
        method: "POST",
        body: JSON.stringify({name: courseName}),
        headers: {"content-type": "application/json"},
    }).then((response) => {
        if (!response.ok) {
            console.error("Houve um error, status: " + response.status)
        }

        loadItems();
        $("#modalDelete").modal("hide");
    })
}

function saveCourse() {
    const nomeCurso = document.getElementById("txtNameCourse").value;

    if(!nomeCurso.value) {
        alert("É necessário informar um nome do curso!");
        return;        
    }
    if (!id){
        create(nomeCurso.value);
    }   else {
        updateCourse(id, nomeCurso.value)
    }
    nomeCurso.value = "";
}
btnConfirmar.addEventListener("click", saveCourse);

//DELETE
function deleteCourse(id) {
    fetch(baseUrl + id, {
        method: "DELETE",
    }).then((response) => {
        if (!response.ok) {
            console.error("Houve um error.");
        }
        loadItems();
    });
}

//PUT
function updateCourse(id, courseName) {
    fetch(baseUrl + id, {
        method: "PUT",
        body: JSON.stringify({ name: courseName }),
        headers: { "Content-Type": "application/json" },
    }).then((response) => {
        if (!response.ok) {
            console.log("Houve um error.");
        }
        response.json().then((json) => {    
            console.log(json);
        });
    });
}
        

function createListItem(course) {
    const item = document.createElement("li");

    const span = document.createElement("span")
    item.textContent = course.name;

    const contentBtns = document.createElement("div");

    const btnEdit = document.createElement("button");
    btnEdit.textContent = "Editar";
    btnEdit.type = "button";
    btnEdit.classList.add("btn");
    btnEdit.classList.add("btn-warning");
    btnEdit.addEventListener("click", () => handleClickEdit(course.id));

    const btnDelete = document.createElement("button");
    btnDelete.textContent = "Remover";
    btnDelete.type = "button";
    btnDelete.classList.add("btn");
    btnDelete.classList.add("btn-danger");
    btnDelete.addEventListener("click", () => deleteCourse(course.id));

    contentBtns.appendChild(btnDelete);
    contentBtns.appendChild(btnEdit);

    item.appendChild(span);
    item.appendChild(contentBtns);

    listContainer.appendChild(item);
}

async function loadItems() {
    listContainer.innerHTML = "";
    const courses = await getlist();

    courses.forEach((c) => createListItem(c));
}
loadItems();
