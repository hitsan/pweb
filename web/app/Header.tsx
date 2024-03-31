import Link from 'next/link';
import React from 'react';

const Header = () => {
  return (<header className="py-5 px-10 boader-b flex">
    <div>
        <h1 className="text-2xl font-extrabold">
            <Link href="/"> test</Link>
        </h1>
    </div>
    <nav>
        <Link href="/articles/new">生地追加</Link>
    </nav>
    </header>);
}

export default Header;