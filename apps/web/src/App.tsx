import './App.css'

function App() {
  return (
    <main className="academy-page">
      <nav className="navbar">
        <div className="brand">
          <span className="brand-mark">J</span>
          <span>Java Engineering Academy</span>
        </div>

        <a className="nav-link" href="#roadmap">
          Learning Roadmap
        </a>
      </nav>

      <section className="hero">
        <div className="hero-content">
          <p className="eyebrow">BUILD REAL ENGINEERING SKILLS</p>

          <h1>
            Learn Java.
            <br />
            Think like an engineer.
          </h1>

          <p className="hero-description">
            A structured, hands-on learning platform for mastering Java,
            data structures, backend engineering, system design, and
            production-ready software development.
          </p>

          <div className="hero-actions">
            <a className="primary-button" href="#roadmap">
              Explore the roadmap
            </a>

            <a className="secondary-button" href="#principles">
              Our approach
            </a>
          </div>
        </div>

        <div className="code-card" aria-label="Java code example">
          <div className="code-header">
            <span className="window-dot red" />
            <span className="window-dot yellow" />
            <span className="window-dot green" />
            <span className="file-name">HelloEngineering.java</span>
          </div>

          <pre>
            <code>
              <span className="keyword">public class</span>{' '}
              <span className="type">HelloEngineering</span> {'{'}
              {'\n  '}
              <span className="keyword">public static void</span>{' '}
              <span className="method">main</span>
              <span className="plain">(String[] args) {'{'}</span>
              {'\n    '}
              <span className="type">System</span>
              <span className="plain">.out.println(</span>
              <span className="string">"Start small. Build deeply."</span>
              <span className="plain">);</span>
              {'\n  }'}
              {'\n}'}
            </code>
          </pre>
        </div>
      </section>

      <section className="section" id="principles">
        <p className="eyebrow">LEARNING PRINCIPLES</p>
        <h2>From syntax to software engineering</h2>

        <div className="feature-grid">
          <article className="feature-card">
            <span className="feature-number">01</span>
            <h3>Understand the foundations</h3>
            <p>
              Build a strong mental model of Java, object-oriented
              programming, memory, collections, and algorithms.
            </p>
          </article>

          <article className="feature-card">
            <span className="feature-number">02</span>
            <h3>Practice through problems</h3>
            <p>
              Solve progressively harder exercises with feedback,
              explanations, tests, and measurable progress.
            </p>
          </article>

          <article className="feature-card">
            <span className="feature-number">03</span>
            <h3>Build production systems</h3>
            <p>
              Apply your knowledge to APIs, databases, backend services,
              architecture, deployment, and real engineering workflows.
            </p>
          </article>
        </div>
      </section>

      <section className="roadmap-section" id="roadmap">
        <p className="eyebrow">THE ROADMAP</p>
        <h2>Your engineering journey</h2>

        <div className="roadmap-list">
          <div className="roadmap-item">
            <span>01</span>
            <div>
              <h3>Java foundations</h3>
              <p>Syntax, types, control flow, methods, classes, and objects.</p>
            </div>
            <strong>Start here</strong>
          </div>

          <div className="roadmap-item">
            <span>02</span>
            <div>
              <h3>Data structures and algorithms</h3>
              <p>
                Problem-solving, complexity, recursion, trees, graphs, and
                dynamic programming.
              </p>
            </div>
            <strong>Next</strong>
          </div>

          <div className="roadmap-item">
            <span>03</span>
            <div>
              <h3>Backend engineering</h3>
              <p>Spring Boot, REST APIs, PostgreSQL, JPA, testing, and security.</p>
            </div>
            <strong>Later</strong>
          </div>
        </div>
      </section>

      <footer>
        <span>Java Engineering Academy</span>
        <span>Learn deeply. Build consistently.</span>
      </footer>
    </main>
  )
}

export default App